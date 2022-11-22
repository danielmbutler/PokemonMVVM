package com.dbtechprojects.pokemonApp.util.testsuites


import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
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
    fun randomPositioningTest(){
        val randomInts = mutableListOf<Int>()
        // 0 to 20 is enough for our usecase
        for (i in 0..20){
            randomInts.add((0..600).random())
        }
        assertEquals(randomInts.size == randomInts.toSet().size, true)
    }


    //test random range


}