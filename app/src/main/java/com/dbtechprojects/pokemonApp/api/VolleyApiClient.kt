package com.dbtechprojects.pokemonApp.api

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.dbtechprojects.pokemonApp.models.api_responses.PokemonDetailItem
import com.dbtechprojects.pokemonApp.models.api_responses.PokemonListItem
import com.google.gson.Gson
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class VolleyApiClient(val context: Context) : PokeApi {
    override suspend fun getPokemonList(): PokemonListItem? = suspendCoroutine { result ->
        val queue = Volley.newRequestQueue(context)
        val url =  "https://pokeapi.co/api/v2/pokemon"
       val request = StringRequest (
            Request.Method.GET, url,
            { response ->
                try {
                    val item = Gson().fromJson(response.toString(), PokemonListItem::class.java)
                    Log.d(" Volley pokemon", "item : $item")

                    result.resume(item)
                }catch (e: Exception){
                    Log.d("pokemon detail", " ${e.message}")
                    result.resume(null)
                }

            },
            { error ->
                Log.d("pokemon detail", " ${error.message}")
                result.resume(null)
            }
        )
        queue.add(request)

    }

    override suspend fun getPokemonDetail(id: String): PokemonDetailItem? = suspendCoroutine{ result ->
        val queue = Volley.newRequestQueue(context)
        val url =  "https://pokeapi.co/api/v2/pokemon/$id"
        Log.d("pokemon detail", "call : $id")
        val request = StringRequest (
            Request.Method.GET, url,
            { response ->
                Log.d("pokemon detail", "call : $response")
                try {

                    val item = Gson().fromJson(response, PokemonDetailItem::class.java)
                    Log.d("Volley", "item : $item")

                    result.resume(item)
                }catch (e: Exception){
                    Log.d("pokemon detail", " ${e.message}")
                    result.resume(null)
                }

            },
            { error ->
                Log.d("pokemon detail", " ${error.message}")
                result.resume(null)
            }
        )

        queue.add(request)
    }
}