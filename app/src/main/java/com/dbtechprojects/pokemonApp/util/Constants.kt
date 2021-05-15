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
                type = "grass",
                positionTop = (0..1500).random(),
                positionLeft = (0..1500).random(),
                apiId = 1
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "ivysaur",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png",
                type = "grass",
                positionTop = (0..1500).random(),
                positionLeft = (0..1500).random(),
                apiId = 2
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "venusaur",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png",
                type = "grass",
                positionTop = (0..1500).random(),
                positionLeft = (0..1500).random(),
                apiId = 3
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "charmander",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png",
                type = "fire",
                positionTop = (0..1500).random(),
                positionLeft = (0..1500).random(),
                apiId = 4
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "charmeleon",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/5.png",
                type = "fire",
                positionTop = (0..1500).random(),
                positionLeft = (0..1500).random(),
                apiId = 5
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "charizard",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png",
                type = "fire",
                positionTop = (0..1500).random(),
                positionLeft = (0..1500).random(),
                apiId = 6
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "squirtle",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png",
                type = "water",
                positionTop = (0..1500).random(),
                positionLeft = (0..1500).random(),
                apiId = 7
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "wartortle",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/8.png",
                type = "water",
                positionTop = (0..1500).random(),
                positionLeft = (0..1500).random(),
                apiId = 8
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "blastoise",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png",
                type = "water",
                positionTop = (0..1500).random(),
                positionLeft = (0..1500).random(),
                apiId = 9
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "caterpie",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/10.png",
                type = "bug",
                positionTop = (0..1500).random(),
                positionLeft = (0..1500).random(),
                apiId = 10
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "metapod",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/11.png",
                type = "bug",
                positionTop = (0..1500).random(),
                positionLeft = (0..1500).random(),
                apiId = 11
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "butterfree",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/12.png",
                type = "bug",
                positionTop = (0..1500).random(),
                positionLeft = (0..1500).random(),
                apiId = 12
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "weedle",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/13.png",
                type = "bug",
                positionTop = (0..1500).random(),
                positionLeft = (0..1500).random(),
                apiId = 13
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "kakuna",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/14.png",
                type = "bug",
                positionTop = (0..1500).random(),
                positionLeft = (0..1500).random(),
                apiId = 14
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "beedrill",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/15.png",
                type = "bug",
                positionTop = (0..1500).random(),
                positionLeft = (0..1500).random(),
                apiId = 15
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "pidgey",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/16.png",
                type = "bird",
                positionTop = (0..1500).random(),
                positionLeft = (0..1500).random(),
                apiId = 16
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "pidgeotto",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/17.png",
                type = "bird",
                positionTop = (0..1500).random(),
                positionLeft = (0..1500).random(),
                apiId = 17
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "pidgeot",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/18.png",
                type = "bird",
                positionTop = (0..1500).random(),
                positionLeft = (0..1500).random(),
                apiId = 18
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "rattata",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/19.png",
                type = "normal",
                positionTop = (0..1500).random(),
                positionLeft = (0..1500).random(),
                apiId = 19
            )
        )
        customPokemonListItem.add(
            CustomPokemonListItem(
                name = "raticate",
                Image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/20.png",
                type = "normal",
                positionTop = (0..1500).random(),
                positionLeft = (0..1500).random(),
                apiId = 20

            )
        )

        return customPokemonListItem

    }
}