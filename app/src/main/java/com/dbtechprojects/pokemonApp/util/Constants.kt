package com.dbtechprojects.pokemonApp.util

import com.dbtechprojects.pokemonApp.models.customModels.CustomPokemonListItem

object Constants {
    const val BASE_URL = "https://pokeapi.co/"
    const val CACHE = 300000 //ROOM DB CACHE (value used to determine whether we should return from the db or request a new Item from API

    // 300000 5 minutes
    // 600000 10 minutes

    // function to preSeed DB on first app run

    fun preSeedDB(): List<CustomPokemonListItem> {
        val customPokemonListItem = mutableListOf<CustomPokemonListItem>()

        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "bulbasaur",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                type = "grass"
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "ivysaur",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png",
                type = "grass"
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "venusaur",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png",
                type = "grass"
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "charmander",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png",
                type = "fire"
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "charmeleon",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/5.png",
                type = "fire"
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "charizard",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png",
                type = "fire"
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "squirtle",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png",
                type = "water"
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "wartortle",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/8.png",
                type = "water"
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "blastoise",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png",
                type = "water"
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "caterpie",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/10.png",
                type = "bug"
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "metapod",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/11.png",
                type = "bug"
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "butterfree",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/12.png",
                type = "bug"
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "weedle",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/13.png",
                type = "bug"
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "kakuna",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/14.png",
                type = "bug"
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "beedrill",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/15.png",
                type = "bug"
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "pidgey",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/16.png",
                type = "bird"
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "pidgeotto",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/17.png",
                type = "bird"
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "pidgeot",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/18.png",
                type = "bird"
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "rattata",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/19.png",
                type = "normal"
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "raticate",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/20.png",
                type = "normal"
            )
        )

        return customPokemonListItem

    }
}