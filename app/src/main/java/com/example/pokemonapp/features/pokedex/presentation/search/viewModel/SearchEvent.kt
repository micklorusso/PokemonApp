package com.example.pokemonapp.features.pokedex.presentation.search.viewModel

sealed class SearchEvent {
    data class OnPokemonClick(val id: Int): SearchEvent()
    data class OnSearchQueryChanged(val newQuery: String): SearchEvent()
}