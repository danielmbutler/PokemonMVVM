package com.dbtechprojects.pokemonApp.api


import com.dbtechprojects.pokemonApp.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FakeApiImplementation {
    fun provideApi(): FakeApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FakeApi::class.java)
}