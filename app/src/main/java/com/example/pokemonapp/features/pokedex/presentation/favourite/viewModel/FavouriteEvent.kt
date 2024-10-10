package com.example.pokemonapp.features.pokedex.presentation.favourite.viewModel


sealed class FavouriteEvent {
    data class OnPokemonClick(val id: Int): FavouriteEvent()
}