package com.dbtechprojects.gamedeals.api

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dbtechprojects.pokemonApp.api.FakeApiImplementation
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ApiIsolationTests : TestCase() {

    @Test
    fun test_pokemon_Api_Result_Not_Empty(){
        val api = FakeApiImplementation.provideApi()
        val test = runBlocking {
            api.getPokemonDetail("1")
        }

        assertEquals(test.isSuccessful, true)
    }

    @Test
    fun test_Game_Store_Api_Result_Not_Empty(){
        val api = FakeApiImplementation.provideApi()
        val test = runBlocking {
            api.getPokemonList()
        }

        assertEquals(test.isSuccessful, true)
    }
}