package com.example.pokemonapp.features.pokedex.presentation.pokemonDetail.viewModel

import com.example.pokemonapp.features.pokedex.domain.entities.PokemonDetailEntity

sealed class PokemonDetailState {

    class Success(val pokemonDetailModel: PokemonDetailEntity): PokemonDetailState()
    class Failure(val errorText: String): PokemonDetailState()
    data object Loading : PokemonDetailState()
    data object Empty : PokemonDetailState()

}