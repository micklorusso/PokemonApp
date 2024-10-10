package com.example.pokemonapp.features.pokedex.presentation.pokemonDetail.viewModel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.pokemonapp.features.auth.data.model.Favourite
import com.example.pokemonapp.features.auth.domain.FavouriteService
import com.example.pokemonapp.features.auth.domain.FirebaseService
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @OptIn(ExperimentalPagingApi::class)
@Inject constructor(
    private val repository: PokemonRepository,
    private val favouriteService: FavouriteService,
    private val firebaseService: FirebaseService
): ViewModel() {
    private val _state = MutableStateFlow<PokemonDetailState>(PokemonDetailState.Empty)
    val state: StateFlow<PokemonDetailState> = _state.asStateFlow()

    private val _uiSingleTimeEvent = MutableSharedFlow<UiSingleTimeEvent>()
    val uiSingleTimeEvent = _uiSingleTimeEvent.asSharedFlow()

    private val _isFavourite = MutableStateFlow<Boolean>(false)
    val isFavourite: StateFlow<Boolean> = _isFavourite.asStateFlow()

    var pokemonId: Int? = null

    init {
        viewModelScope.launch {
            _state.collectLatest {
                if(state.value is PokemonDetailState.Success){
                    val pokemonDetail = (state.value as PokemonDetailState.Success).pokemonDetailModel
                    val favourite = firebaseService.favourite.first()
                    pokemonId = pokemonDetail.id
                    for(pokemon in favourite)
                        if(pokemon.pokemonId.toInt() == pokemonId)
                            _isFavourite.value = true

                }
            }
        }

    }

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
            is PokemonDetailEvent.OnHartClick -> {
                viewModelScope.launch {
                    if(!isFavourite.value) {
                        favouriteService.addFavourite(
                            firebaseService.user.first().id,
                            Favourite.toFavourite(event.pokemon)
                        )
                        _isFavourite.value = true
                    }
                    else{
                        favouriteService.removeFavourite(firebaseService.user.first().id,
                            pokemonId.toString()
                        )
                        _isFavourite.value = false
                    }
                }
            }
        }
    }
}