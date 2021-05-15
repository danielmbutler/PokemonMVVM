package com.dbtechprojects.pokemonApp.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dbtechprojects.pokemonApp.models.api_responses.PokemonDetailItem
import com.dbtechprojects.pokemonApp.models.customModels.CustomPokemonListItem

//DAO Class to provide methods to perform options on Room Database

@Dao
interface PokemonDao {

    //pokemon table functions


    // searches Db and returns result if a name contains the string provided from the user
    @Query("SELECT * FROM pokemon WHERE name LIKE '%' || :name || '%'")
    suspend fun searchPokemonByName(name: String): List<CustomPokemonListItem>?

    // returns exact type matches from DB
    @Query("SELECT * FROM pokemon WHERE type Like :type")
    suspend fun searchPokemonByType(type: String): List<CustomPokemonListItem>?

    @Query("SELECT * FROM pokemon")
    fun getPokemon(): List<CustomPokemonListItem>

    @Query("SELECT * FROM pokemon WHERE isSaved = 'true'")
    suspend fun getSavedPokemon(): List<CustomPokemonListItem>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPokemonList(list: List<CustomPokemonListItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemon(item: CustomPokemonListItem)


    // pokemonDetails table functions

    @Query("SELECT * FROM pokemonDetails WHERE id Like :id")
    suspend fun getPokemonDetails(id: String): PokemonDetailItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonDetailsItem(pokemonDetailItem: PokemonDetailItem)

    // WorkManger Functions

    @Query("SELECT * FROM pokemon ORDER BY id DESC LIMIT 1")
    fun getLastStoredPokemonObject(): CustomPokemonListItem

}