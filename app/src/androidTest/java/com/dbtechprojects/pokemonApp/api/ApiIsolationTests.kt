package com.dbtechprojects.pokemonApp.api

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ApiIsolationTests : TestCase() {

    @Test
    fun test_pokemon_Api_Detail_Result_Success(){
        val api = TestApiImplementation.provideApi()
        val test = runBlocking {
            api.getPokemonDetail("1")
        }

        assertEquals(test.isSuccessful, true)
    }

    @Test
    fun  test_pokemon_Api_List_Result_Success(){
        val api = TestApiImplementation.provideApi()
        val test = runBlocking {
            api.getPokemonList()
        }

        assertEquals(test.isSuccessful, true)
    }

    @Test
    fun  test_pokemon_Api_List_Result_NotEmpty(){
        val api = TestApiImplementation.provideApi()
        val test = runBlocking {
            api.getPokemonList()
        }

        assertEquals(test.body()?.results?.isNotEmpty(), true)
    }


    @Test
    fun test_pokemon_Api_Detail_Result_Not_Null(){
        val api = TestApiImplementation.provideApi()
        val test = runBlocking {
            api.getPokemonDetail("1")
        }

        assertEquals(test.body() != null, true)
    }
}