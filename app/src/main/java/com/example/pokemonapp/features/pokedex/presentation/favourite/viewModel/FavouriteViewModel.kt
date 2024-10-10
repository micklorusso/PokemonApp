package com.example.pokemonapp.features.pokedex.presentation.favourite.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.features.auth.data.model.Favourite
import com.example.pokemonapp.features.auth.domain.AccountService
import com.example.pokemonapp.features.auth.domain.FavouriteService
import com.example.pokemonapp.features.auth.domain.FirebaseService
import com.example.pokemonapp.navigation.Screen
import com.example.pokemonapp.util.UiSingleTimeEvent
import com.example.pokemonapp.util.UiSingleTimeEvent.Companion.emitUiSingleTimeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    val accountService: AccountService,
    val firebaseService: FirebaseService
): ViewModel(){
    private val _uiSingleTimeEvent = MutableSharedFlow<UiSingleTimeEvent>()
    val uiSingleTimeEvent = _uiSingleTimeEvent.asSharedFlow()

    fun onEvent(event: FavouriteEvent){
        when(event){
            is FavouriteEvent.OnPokemonClick -> {
                emitUiSingleTimeEvent(UiSingleTimeEvent.Navigate("${Screen.PokemonDetailScreen.route}/${event.id}"), viewModelScope, _uiSingleTimeEvent)
            }
        }
    }
}