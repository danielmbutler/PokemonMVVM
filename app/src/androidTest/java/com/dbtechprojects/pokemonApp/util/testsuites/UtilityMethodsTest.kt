package com.dbtechprojects.pokemonApp.util.testsuites



import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dbtechprojects.pokemonApp.models.api_responses.Converters
import com.dbtechprojects.pokemonApp.models.api_responses.NameAndUrl
import com.dbtechprojects.pokemonApp.models.api_responses.PokemonStat
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith



// class to test room db

@RunWith(AndroidJUnit4::class)
class UtilityMethodsTest : TestCase() {

    /**
     * test random positioning used in detail viewmodel
     * @see com.dbtechprojects.pokemonApp.ui.viewmodels.DetailViewModel.plotLeft
     */
    @Test
    fun randomPositioningTest() {
        val randomInts = mutableListOf<Int>()
        // 0 to 20 is enough for our usecase
        for (i in 0..20) {
            randomInts.add((0..600).random())
        }
        assertEquals(randomInts.size == randomInts.toSet().size, true)
    }


    //test random range
    @Test
    fun randomRangeTest() {
        val randomInts = mutableListOf<Int>()
        for (i in 0..200) {
            randomInts.add((0..600).random())
        }
        assertEquals(randomInts.filter { (0..600).contains(it) }.size == randomInts.size, true)
    }

    @Test
    fun typeConverterTest(){
        val data = listOf(PokemonStat(baseStat = 6, effort = 6, stat = NameAndUrl("test", "test")))
        val converters = Converters()
        val string = converters.fromPokemonStat(data)
        assertEquals(data == converters.toPokemonStat(string), true)
    }


}