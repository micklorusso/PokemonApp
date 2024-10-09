package com.example.pokemonapp.features.pokedex.presentation.pokemonDetail.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.pokemonapp.features.pokedex.domain.entities.PokemonDetailEntity
import com.example.pokemonapp.features.pokedex.domain.repositories.PokemonRepository
import com.example.pokemonapp.util.Resource
import com.example.pokemonapp.util.UiSingleTimeEvent
import com.example.pokemonapp.util.UiSingleTimeEvent.Companion.emitUiSingleTimeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
    private val repository: PokemonRepository
): ViewModel() {
    private val _state = MutableStateFlow<PokemonDetailState>(PokemonDetailState.Empty)
    val state: StateFlow<PokemonDetailState> = _state.asStateFlow()

    private val _uiSingleTimeEvent = MutableSharedFlow<UiSingleTimeEvent>()
    val uiSingleTimeEvent = _uiSingleTimeEvent.asSharedFlow()

    @OptIn(ExperimentalPagingApi::class)
    fun getPokemonDetail(id: Int) {
        viewModelScope.launch{
            _state.value = PokemonDetailState.Loading
            when(val pokemonDetailResource = repository.getPokemonDetail(id)){
                is Resource.Error -> _state.value =
                    PokemonDetailState.Failure(pokemonDetailResource.message!!)
                is Resource.Success -> {
                    val pokemonDetail: PokemonDetailEntity? = pokemonDetailResource.data
                    if (pokemonDetail == null) {
                        _state.value =
                            PokemonDetailState.Failure("/pokemon/$id: null model returned")
                    } else {
                        _state.value = PokemonDetailState.Success(pokemonDetail)
                    }
                }
            }
        }
    }

    fun onEvent(event: PokemonDetailEvent){
        when(event){
            is PokemonDetailEvent.OnBackClicked -> {
                emitUiSingleTimeEvent(UiSingleTimeEvent.PopBackStack, viewModelScope, _uiSingleTimeEvent)
            }
        }
    }
}