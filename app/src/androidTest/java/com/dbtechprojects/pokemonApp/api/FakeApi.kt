package com.dbtechprojects.pokemonApp.api

import com.dbtechprojects.pokemonApp.models.api_responses.PokemonDetailItem
import com.dbtechprojects.pokemonApp.models.api_responses.PokemonListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface FakeApi {
    @GET("api/v2/pokemon")
    suspend fun getPokemonList(): Response<PokemonListItem>


    @GET("/api/v2/pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id: String,

        ): Response<PokemonDetailItem>



}