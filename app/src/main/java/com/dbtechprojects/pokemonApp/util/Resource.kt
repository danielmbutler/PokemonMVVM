package com.dbtechprojects.pokemonApp.util

//Resource Wrapper class for Objects retrieved from Repository

sealed class Resource<T>(val data: T?, val message: String?) {
    class Success<T>(data: T) : Resource<T>(data, null)
    class Error<T>(message: String) : Resource<T>(null, message)
    class Loading<T>(message: String) : Resource<T>(null, message)
    class Expired<T>(message: String, data: T) : Resource<T>(data, message) // used if cache is expired and we cannot return new data from API
}