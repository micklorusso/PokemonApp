package com.example.pokemonapp.features.pokedex.presentation.pokemonDetail.viewModel


import com.example.pokemonapp.features.pokedex.domain.entities.PokemonDetailEntity

sealed class PokemonDetailEvent {
    data object OnBackClicked : PokemonDetailEvent()
    data class OnHartClick(val pokemon: PokemonDetailEntity): PokemonDetailEvent()
}