package com.example.pokemonapp.features.pokedex.presentation.pokemonDetail.viewModel

sealed class PokemonDetailEvent {
    data object OnBackClicked : PokemonDetailEvent()
}