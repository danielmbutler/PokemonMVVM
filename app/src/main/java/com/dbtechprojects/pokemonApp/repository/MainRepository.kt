package com.dbtechprojects.pokemonApp.repository

import android.util.Log
import com.dbtechprojects.pokemonApp.api.PokeApi
import com.dbtechprojects.pokemonApp.models.api_responses.PokemonDetailItem
import com.dbtechprojects.pokemonApp.models.customModels.CustomPokemonListItem
import com.dbtechprojects.pokemonApp.persistence.PokemonDao
import com.dbtechprojects.pokemonApp.util.Constants
import com.dbtechprojects.pokemonApp.util.Resource
import java.lang.Exception
import javax.inject.Inject


// class to implement methods from default repository interface, query remote datasources and return results to viewModels

private const val TAG = "MainRepository"

class MainRepository @Inject constructor(
    private val pokeApi: PokeApi,
    private val pokeDao: PokemonDao
) : DefaultRepository {

    // used to confirm whether items where stored longer than 5 mins ago
    private val fiveMinutesAgo = System.currentTimeMillis() - Constants.CACHE


    override suspend fun getPokemonList(): Resource<List<CustomPokemonListItem>> {

        // check for results from DB
        val responseFromDB = pokeDao.getPokemon()
        if (responseFromDB.isNotEmpty()) {
            return Resource.Success(responseFromDB)
        } else {
            // if return null then preSeed from Constants
            val preSeedList = Constants.preSeedDB()

            // insert into DB
            pokeDao.insertPokemonList(preSeedList)

            // read from DB
            val initialDBRead = pokeDao.getPokemon()

            // return from DB
            return Resource.Success(initialDBRead)

        }


    }

    override suspend fun getPokemonListNextPage(): Resource<List<CustomPokemonListItem>> {
        // get id of last Pokemon in local DB
        val lastStoredPokemonObject = getLastStoredPokemon()

        // check API for details on next pokemon
        val nextPokemon = lastStoredPokemonObject.apiId + 1
        val pokemonList = mutableListOf<CustomPokemonListItem>()

        for (i in nextPokemon..(nextPokemon + 9)){
            when (val apiResult = getPokemonDetail(i)) {
                is Resource.Success -> {

                    if (apiResult.data != null) {
                        apiResult.data.let { newPokemon ->
                            // create custom pokemon object save in DB
                            val newPokemonObj = CustomPokemonListItem(
                                name = newPokemon.name,
                                Image = newPokemon.sprites.front_default,
                                type = newPokemon.types?.get(0)?.type?.name.toString(),
                                // set positions for map
                                positionLeft = (0..1500).random(),
                                positionTop = (0..1500).random(),
                                apiId = newPokemon.id
                            )
                            savePokemon(newPokemonObj)

                            pokemonList.add(newPokemonObj)

                        }
                    } else {
                        return Resource.Error("unable to retrieve next items")
                    }
                }
                else -> return Resource.Error("unable to retrieve next items")
            }
        }

        return Resource.Success(pokemonList)

    }

    override suspend fun getPokemonSavedPokemon(): Resource<List<CustomPokemonListItem>> {
        val dbResult = pokeDao.getSavedPokemon()
        return if (dbResult.isNullOrEmpty()) {
            Resource.Error("saved pokemon list is empty")
        } else {
            Resource.Success(dbResult)
        }
    }

    private suspend fun getPokemonDetailFromApi(id: Int, dbResult: PokemonDetailItem?, ): Resource<PokemonDetailItem>{
        try {
            val apiResult = pokeApi.getPokemonDetail(id)
            if (apiResult.isSuccessful) {

                if (apiResult.body() != null) {

                    // add timestamp
                    val newPokemon = apiResult.body()
                    newPokemon!!.timestamp = System.currentTimeMillis().toString()

                    // store results in DB
                    pokeDao.insertPokemonDetailsItem(newPokemon)
                    // retrieve results from DB

                    val newDBRead = pokeDao.getPokemonDetails(id)

                    // return from DB
                    return Resource.Success(newDBRead!!)
                } else if (dbResult != null) {
                    // return expired object to let user know cache has expired and we cannot find new items from Api
                    return Resource.Expired("Cache expired and cannot retrieve new Pokemon please check network connectivity ", dbResult)
                }
            } else {
                return Resource.Error(apiResult.message())
            }
        } catch (e: Exception) {
            return Resource.Error("error retrieving results")
        }
        return Resource.Error("error retrieving results")
    }

    // Single Source of Truth function
    // retrieve pokemon from DB if empty ,retrieve pokemon from api
    // if timestamp expired retrieve from api, insert results to db , updated timestamps and return items from db
    override suspend fun getPokemonDetail(id: Int): Resource<PokemonDetailItem> {
        // first check DB for results
        val dbResult = pokeDao.getPokemonDetails(id)

        if (dbResult != null) {
            //if cache is older than 5 mins ago lets check the api for new results
            return if (dbResult.timestamp?.toLong()!! < fiveMinutesAgo) {

                Log.d(TAG, "CACHE EXPIRED RETRIEVING NEW ITEM")

                getPokemonDetailFromApi(id, dbResult)
            } else {
                // cache is sufficient lets return it
                Resource.Success(dbResult)
            }

        } else {
            // DB is empty so lets check the api
           return getPokemonDetailFromApi(id, null)
        }


    }

    override suspend fun getLastStoredPokemon(): CustomPokemonListItem {
        return pokeDao.getLastStoredPokemonObject()
    }



    override suspend fun searchPokemonByName(name: String): Resource<List<CustomPokemonListItem>> {
        val dbResult = pokeDao.searchPokemonByName(name)

        return if (dbResult != null) {
            Log.d(TAG, dbResult.toString())
            Resource.Success(dbResult)

        } else {
            Resource.Error("no pokemon found")
        }
    }

    override suspend fun searchPokemonByType(type: String): Resource<List<CustomPokemonListItem>> {
        val dbResult = pokeDao.searchPokemonByType(type)

        return if (dbResult != null) {
            Resource.Success(dbResult)
        } else {
            Resource.Error("no pokemon found")
        }
    }

    override suspend fun savePokemon(pokemonListItem: CustomPokemonListItem) {
        pokeDao.insertPokemon(pokemonListItem)
    }

}