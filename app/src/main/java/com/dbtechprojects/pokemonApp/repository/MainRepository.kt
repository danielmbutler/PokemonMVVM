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

    override suspend fun getPokemonSavedPokemon(): Resource<List<CustomPokemonListItem>> {
        val dbResult = pokeDao.getSavedPokemon()
        if (dbResult.isNullOrEmpty()) {
            return Resource.Error("saved pokemon list is empty")
        } else {
            return Resource.Success(dbResult)
        }
    }

    // Single Source of Truth function
    // retrieve pokemon from DB if empty retrieve pokemon from api
    // if timestamp expired retrieve from api, insert results to db , updated timestamps and return items from db

    override suspend fun getPokemonDetail(id: String): Resource<PokemonDetailItem> {
        // first check DB for results
        val dbResult = pokeDao.getPokemonDetails(id)

        if (dbResult != null) {

            //if cache is older than 5 mins ago lets check the api for new results
            if (dbResult.timestamp?.toLong()!! < fiveMinutesAgo) {

                Log.d(TAG, "CACHE EXPIRED RETRIEVING NEW ITEM")

                // could throw exception if no internet available

                try {
                    val apiResult = pokeApi.getPokemonDetail(id)
                    if (apiResult != null) {

                        // add timestamp
                        apiResult.timestamp = System.currentTimeMillis().toString()

                        // store results in DB
                        pokeDao.insertPokemonDetailsItem(apiResult)
                        // retrieve results from DB

                        val newDBRead = pokeDao.getPokemonDetails(id)

                        // return from DB

                        return Resource.Success(newDBRead!!)

                    } else {
                        return Resource.Error("error retrieving results")
                    }
                } catch (e: Exception) {
                    return Resource.Error("error retrieving results")
                }
                // check if response is successful


            } else {
                // cache is sufficient let return it
                Log.d(TAG, "CACHE IS SUFFICIENT RETURN ITEM FROM DB")
                Log.d(TAG, "CACHED TIME : ${dbResult.timestamp} $fiveMinutesAgo")
                return Resource.Success(dbResult)
            }

        } else {
            // DB is empty so lets check the api
            // could throw exception if no internet available

            try {
                // check if response is successful
                val apiResult = pokeApi.getPokemonDetail(id)
                if (apiResult != null) {

                    // add timestamp
                    apiResult.timestamp = System.currentTimeMillis().toString()

                    // store results in DB
                    pokeDao.insertPokemonDetailsItem(apiResult)
                    // retrieve results from DB

                    val newDBRead = pokeDao.getPokemonDetails(id)

                    // return from DB
                    Log.d(TAG, "NO ITEM IN DB FOUND, ITEM HAS BEEN RETRIEVED FROM API")
                    return Resource.Success(newDBRead!!)
                } else {
                    return Resource.Error("error retrieving results")
                }
            } catch (e: Exception) {
                return Resource.Error("error retrieving results")
            }


        }


    }


    // WorkManager

    override suspend fun getLastStoredPokemon(): CustomPokemonListItem {
        return pokeDao.getLastStoredPokemonObject()
    }

    override suspend fun searchPokemonByName(name: String): Resource<List<CustomPokemonListItem>> {
        val dbResult = pokeDao.searchPokemonByName(name)

        if (dbResult != null) {
            Log.d(TAG, dbResult.toString())
            return Resource.Success(dbResult)

        } else {
            Log.d(TAG, dbResult.toString())
            return Resource.Error("no pokemon found")
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