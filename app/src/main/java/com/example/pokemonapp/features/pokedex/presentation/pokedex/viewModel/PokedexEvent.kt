package com.example.pokemonapp.features.pokedex.presentation.pokedex.viewModel

sealed class PokedexEvent {
    data class OnPokemonClick(val id: Int, val color: String): PokedexEvent()
}