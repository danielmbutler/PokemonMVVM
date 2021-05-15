package com.dbtechprojects.pokemonApp.models.api_responses

import com.google.gson.annotations.SerializedName

// data class for PokemonListItem


data class PokemonListItem(
    @SerializedName("count")
    val count: Int,

    @SerializedName("next")
    val next: String,

    @SerializedName("previous")
    val previous: Any,

    @SerializedName("results")
    val results: List<PokemonResult>
)

data class PokemonResult(
    val name: String,
    val url: String
)

