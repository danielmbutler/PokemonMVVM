package com.dbtechprojects.pokemonApp.api

import com.dbtechprojects.pokemonApp.models.api_responses.PokemonDetailItem
import com.dbtechprojects.pokemonApp.models.api_responses.PokemonListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

// API Interface class to retrieve Pokemon Items and Pokemon Details

interface PokeApi {

    @GET("api/v2/pokemon")
    suspend fun getPokemonList(): Response<PokemonListItem>


    @GET("/api/v2/pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id: String,

        ): Response<PokemonDetailItem>

}

//Base Url
//"https://pokeapi.co/"

// GET PokemonDetail
//https://pokeapi.co/api/v2/pokemon/{number}/

//GET PokemonList
//https://pokeapi.co/api/v2/pokemon/
