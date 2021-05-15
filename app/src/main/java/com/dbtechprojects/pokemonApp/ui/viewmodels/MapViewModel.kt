package com.dbtechprojects.pokemonApp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbtechprojects.pokemonApp.models.api_responses.PokemonDetailItem
import com.dbtechprojects.pokemonApp.models.api_responses.PokemonListItem
import com.dbtechprojects.pokemonApp.models.api_responses.PokemonResult
import com.dbtechprojects.pokemonApp.models.customModels.CustomPokemonListItem
import com.dbtechprojects.pokemonApp.repository.DefaultRepository
import com.dbtechprojects.pokemonApp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val repository: DefaultRepository) : ViewModel() {
    private val _pokemonList = MutableLiveData<Resource<List<CustomPokemonListItem>>>()
    val pokemonList: LiveData<Resource<List<CustomPokemonListItem>>>
        get() = _pokemonList


    fun getPokemonList() {
        _pokemonList.postValue(Resource.Loading("loading"))
        Log.d("ViewModel", "function called")
        viewModelScope.launch(Dispatchers.IO) {
            _pokemonList.postValue(repository.getPokemonList())
        }
    }

}