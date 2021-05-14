package com.dbtechprojects.pokemonApp.models.api_responses

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize


// make Pokemon name unique to stop duplicate Items
@Entity(
    tableName = "pokemonDetails", indices = (arrayOf(Index(value = arrayOf("name"), unique = true)))
)
data class PokemonDetailItem(
    @SerializedName("id")
    @PrimaryKey
    val id: Int,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "timestamp")
    var timestamp: String? = "", // additional value needed to invalidate cache

    @SerializedName("abilities")
    @ColumnInfo(name = "abilities")
    val abilities: List<PokemonAbility>,

    @SerializedName("stats")
    @ColumnInfo(name = "stats")
    val stat: List<PokemonStat>,

    @SerializedName("types")
    @ColumnInfo(name = "types")
    val types: List<PokemonType>?,

    @SerializedName("sprites")
    @Embedded
    val sprites: Sprites
)

data class PokemonAbility(
    @SerializedName("ability")
    val ability: NameAndUrl,

    @SerializedName("is_hidden")
    val isHidden: Boolean,

    @SerializedName("slot")
    val slot: Int
)

data class PokemonStat(
    @SerializedName("base_stat")
    val baseStat: Int?,

    @SerializedName("effort")
    val effort: Int?,

    @SerializedName("stat")
    val stat: NameAndUrl
)

data class PokemonType(
    @SerializedName("slot")
    val slot: Int,

    @SerializedName("type")
    val type: NameAndUrl
)

data class Sprites(
    val front_default: String,
    @Embedded @SerializedName("other") val otherSprites: OtherSprites
)

data class OtherSprites(
    @Embedded(prefix = "official_") @SerializedName("official-artwork") val artwork: OfficialArtwork
)

data class OfficialArtwork(
    val front_default: String?
)


data class NameAndUrl(
    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
)

// Type Converters to show Room DB how to store these custom objects

class Converters {
    @TypeConverter
    fun FromPokemonAbility(list: List<PokemonAbility?>?): String? {
        val type = object : TypeToken<List<PokemonAbility>>() {}.type
        return Gson().toJson(list, type)
    }

    @TypeConverter
    fun toPokemonAbility(list: String?): List<PokemonAbility>? {
        val type = object : TypeToken<List<PokemonAbility>>() {}.type
        return Gson().fromJson<List<PokemonAbility>>(list, type)
    }

    @TypeConverter
    fun fromPokemonStat(list: List<PokemonStat?>?): String? {
        val type = object : TypeToken<List<PokemonStat>>() {}.type
        return Gson().toJson(list, type)
    }

    @TypeConverter
    fun toPokemonStat(list: String?): List<PokemonStat>? {
        val type = object : TypeToken<List<PokemonStat>>() {}.type
        return Gson().fromJson<List<PokemonStat>>(list, type)
    }

    @TypeConverter
    fun fromPokemonType(list: List<PokemonType?>?): String? {
        val type = object : TypeToken<List<PokemonType>>() {}.type
        return Gson().toJson(list, type)
    }

    @TypeConverter
    fun toPokemonType(list: String?): List<PokemonType>? {
        val type = object : TypeToken<List<PokemonType>>() {}.type
        return Gson().fromJson<List<PokemonType>>(list, type)
    }


}

