package com.example.pokemonapp.features.pokedex.presentation

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.pokemonapp.features.pokedex.domain.repositories.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class PokedexViewModel @Inject constructor(
    repository: PokemonRepository
): ViewModel() {
    val getPokemon = repository.getPokemon()
}