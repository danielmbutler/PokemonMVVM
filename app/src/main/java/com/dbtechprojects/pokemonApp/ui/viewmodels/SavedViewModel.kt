package com.dbtechprojects.pokemonApp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbtechprojects.pokemonApp.models.customModels.CustomPokemonListItem
import com.dbtechprojects.pokemonApp.repository.DefaultRepository
import com.dbtechprojects.pokemonApp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(private val repository: DefaultRepository) : ViewModel() {

    private val _savedPokemon = MutableLiveData<Resource<List<CustomPokemonListItem>>>()
    val savedPokemon: LiveData<Resource<List<CustomPokemonListItem>>>
        get() = _savedPokemon


    fun getPokemonSavedPokemon() {
        _savedPokemon.postValue(Resource.Loading("loading"))
        viewModelScope.launch(Dispatchers.IO) {
            _savedPokemon.postValue(repository.getPokemonSavedPokemon())
        }
    }

    fun deletePokemon(customPokemonListItem: CustomPokemonListItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.savePokemon(customPokemonListItem)
        }
    }


}