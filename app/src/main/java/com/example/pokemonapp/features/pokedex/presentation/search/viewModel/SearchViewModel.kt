package com.example.pokemonapp.features.pokedex.presentation.search.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.pokemonapp.features.auth.domain.FirebaseService
import com.example.pokemonapp.features.pokedex.domain.entities.PokemonListItemEntity
import com.example.pokemonapp.features.pokedex.domain.repositories.PokemonRepository
import com.example.pokemonapp.features.pokedex.presentation.pokedex.viewModel.PokedexEvent
import com.example.pokemonapp.navigation.Screen
import com.example.pokemonapp.util.UiSingleTimeEvent
import com.example.pokemonapp.util.UiSingleTimeEvent.Companion.emitUiSingleTimeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    repository: PokemonRepository,
    val firebaseService: FirebaseService
): ViewModel() {
    val pokedex = repository.getPokedex().cachedIn(viewModelScope)

    private val _uiSingleTimeEvent = MutableSharedFlow<UiSingleTimeEvent>()
    val uiSingleTimeEvent = _uiSingleTimeEvent.asSharedFlow()

    private val _filteredItems = MutableStateFlow<PagingData<PokemonListItemEntity>>(PagingData.empty())
    val filteredItems: StateFlow<PagingData<PokemonListItemEntity>> = _filteredItems

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        viewModelScope.launch {
            combine(_searchQuery, pokedex) { query, pokedexList ->
                if (query.isEmpty()) {
                    pokedexList
                } else {
                    pokedexList.filter { pokemon ->
                        pokemon.types.joinToString("").contains(query, ignoreCase = true)
                    }
                }
            }.collect { filteredList ->
                _filteredItems.value = filteredList
            }
        }
    }

    fun onEvent(event: SearchEvent){
        when(event){
            is SearchEvent.OnPokemonClick -> {
                emitUiSingleTimeEvent(UiSingleTimeEvent.Navigate("${Screen.PokemonDetailScreen.route}/${event.id}"), viewModelScope, _uiSingleTimeEvent)
            }
            is SearchEvent.OnSearchQueryChanged -> {
                _searchQuery.value = event.newQuery
            }
        }
    }


}