package com.dbtechprojects.pokemonApp.models.customModels

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dbtechprojects.pokemonApp.models.api_responses.PokemonType
import kotlinx.parcelize.Parcelize

// model class Custom Pokemon Items to be shown in List Fragment

@Parcelize   // make Pokemon name unique to stop duplicate Items
@Entity(
    tableName = "pokemon", indices = (arrayOf(Index(value = arrayOf("name"), unique = true)))
)
data class CustomPokemonListItem(
    @ColumnInfo(name = "name")
    val name: String,

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "image")
    val Image: String? =null,

    @ColumnInfo(name = "positionLeft")
    val positionLeft: Int? = null,

    @ColumnInfo(name = "positionTop")
    val positionTop: Int? = null,

    @ColumnInfo(name = "type")
    val type: String, // used for filtering more can be added later

    @ColumnInfo(name = "isSaved")
    var isSaved: String = "false"

) : Parcelable
