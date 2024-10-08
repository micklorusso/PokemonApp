package com.example.pokemonapp.features.pokedex.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems


@ExperimentalPagingApi
@Composable
fun PokedexScreen(
    navController: NavHostController,
    pokedexViewModel: PokedexViewModel = hiltViewModel()
) {
    val pokemonListItemEntities = pokedexViewModel.getPokemon.collectAsLazyPagingItems()

    Scaffold(
        content = {padding ->
            ListContent(pokemonListItemEntities = pokemonListItemEntities, modifier = Modifier.padding(padding))
        }
    )
}