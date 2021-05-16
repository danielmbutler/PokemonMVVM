package com.dbtechprojects.pokemonApp.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dbtechprojects.pokemonApp.models.api_responses.OfficialArtwork
import com.dbtechprojects.pokemonApp.models.api_responses.OtherSprites
import com.dbtechprojects.pokemonApp.models.api_responses.PokemonDetailItem
import com.dbtechprojects.pokemonApp.models.api_responses.Sprites
import com.dbtechprojects.pokemonApp.models.customModels.CustomPokemonListItem
import com.dbtechprojects.pokemonApp.persistence.PokemonDao
import com.dbtechprojects.pokemonApp.persistence.PokemonDatabase
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


// class to test room db

@RunWith(AndroidJUnit4::class)
class RoomDatabaseTest : TestCase() {

    private lateinit var roomDatabase: PokemonDatabase
    private lateinit var roomDAO: PokemonDao

    // setup in memory database

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        roomDatabase = Room.inMemoryDatabaseBuilder(context, PokemonDatabase::class.java).build()
        roomDAO = roomDatabase.pokemonDao()
    }

    @After
    fun closeDb(){
        roomDatabase.close()
    }

    // insert custom Pokemon Item Test

    @Test
    fun insertRoomItem(){
        val roomItem = CustomPokemonListItem(apiId = 1, name = "", type = "")
        roomDAO.insertPokemon(roomItem)

        val roomItemRetrieve = roomDAO.getPokemon()
        assertEquals(roomItemRetrieve.isNotEmpty(), true)

    }

    // insert Pokemon Details Test

    @Test
    fun insertRoomDetailsItem() = runBlocking{

        val roomItem = PokemonDetailItem(
            abilities = listOf(),
            id = 1,
            name = "",
            stat = listOf(),
            types = listOf(),
            sprites = Sprites("", OtherSprites(artwork = OfficialArtwork("")))
        )

        roomDAO.insertPokemonDetailsItem(roomItem)

        val roomItemRetrieve = roomDAO.getPokemonDetails("1")
        assertEquals(roomItemRetrieve != null, true)
    }

}