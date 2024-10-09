package com.example.pokemonapp.features.pokedex.presentation.pokedex.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.pokemonapp.features.pokedex.domain.repositories.PokemonRepository
import com.example.pokemonapp.navigation.Screen
import com.example.pokemonapp.util.UiSingleTimeEvent
import com.example.pokemonapp.util.UiSingleTimeEvent.Companion.emitUiSingleTimeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class PokedexViewModel @Inject constructor(
    repository: PokemonRepository
): ViewModel() {
    val pokedex = repository.getPokedex()

    private val _uiSingleTimeEvent = MutableSharedFlow<UiSingleTimeEvent>()
    val uiSingleTimeEvent = _uiSingleTimeEvent.asSharedFlow()

    fun onEvent(event: PokedexEvent){
        when(event){
            is PokedexEvent.OnPokemonClick -> {
                emitUiSingleTimeEvent(UiSingleTimeEvent.Navigate("${Screen.PokemonDetailScreen.route}/${event.id}&${event.color}"), viewModelScope, _uiSingleTimeEvent)
            }
        }
    }
}