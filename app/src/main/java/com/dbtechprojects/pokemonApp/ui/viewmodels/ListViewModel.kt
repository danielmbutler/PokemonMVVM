package com.dbtechprojects.pokemonApp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbtechprojects.pokemonApp.models.customModels.CustomPokemonListItem
import com.dbtechprojects.pokemonApp.repository.DefaultRepository
import com.dbtechprojects.pokemonApp.util.Resource
import com.dbtechprojects.pokemonApp.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val repository: DefaultRepository) : ViewModel() {
    private val _pokemonList = SingleLiveEvent<Resource<List<CustomPokemonListItem>>>()
    val pokemonList: LiveData<Resource<List<CustomPokemonListItem>>>
        get() = _pokemonList


    init {
        Log.d("oncreate", "oncreate viewmodel")
        getPokemonList()
    }


    fun getPokemonList() {
        _pokemonList.postValue(Resource.Loading("loading"))
        viewModelScope.launch(Dispatchers.IO) {
            _pokemonList.postValue(repository.getPokemonList())
        }
    }

    fun getNextPage(){
        viewModelScope.launch(Dispatchers.IO) {
            _pokemonList.postValue(repository.getPokemonListNextPage())
        }
    }
}