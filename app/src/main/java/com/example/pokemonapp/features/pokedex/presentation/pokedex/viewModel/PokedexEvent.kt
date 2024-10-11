package com.example.pokemonapp.features.pokedex.presentation.pokedex.viewModel

sealed class PokedexEvent {
    data class OnPokemonClick(val id: Int): PokedexEvent()
    data object OnProfileClick: PokedexEvent()
    data object OnSettingsClick: PokedexEvent()
    data object OnFavouritesClick: PokedexEvent()
    data object OnFilterClick: PokedexEvent()
}