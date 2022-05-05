package com.dbtechprojects.pokemonApp.repository

import com.dbtechprojects.pokemonApp.models.api_responses.PokemonDetailItem
import com.dbtechprojects.pokemonApp.models.customModels.CustomPokemonListItem
import com.dbtechprojects.pokemonApp.util.Resource


// interface class to be overridden by main Repository

interface DefaultRepository {
    suspend fun getPokemonList(): Resource<List<CustomPokemonListItem>>
    suspend fun getPokemonListNextPage(): Resource<List<CustomPokemonListItem>>
    suspend fun getPokemonSavedPokemon(): Resource<List<CustomPokemonListItem>>
    suspend fun getPokemonDetail(id: Int): Resource<PokemonDetailItem>
    suspend fun getLastStoredPokemon(): CustomPokemonListItem
    suspend fun searchPokemonByName(name: String): Resource<List<CustomPokemonListItem>>
    suspend fun searchPokemonByType(type: String): Resource<List<CustomPokemonListItem>>
    suspend fun savePokemon(pokemonListItem: CustomPokemonListItem)

}