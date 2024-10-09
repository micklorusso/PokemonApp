package com.example.pokemonapp.features.pokedex.presentation.pokedex.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokemonapp.features.pokedex.presentation.pokedex.viewModel.PokedexEvent
import com.example.pokemonapp.features.pokedex.presentation.pokedex.viewModel.PokedexViewModel
import com.example.pokemonapp.util.UiSingleTimeEvent


@ExperimentalPagingApi
@Composable
fun PokedexScreen(
    navController: NavHostController,
    pokedexViewModel: PokedexViewModel = hiltViewModel()
) {
    val pokemonListItemEntities = pokedexViewModel.pokedex.collectAsLazyPagingItems()
    LaunchedEffect(key1 = true) {
        pokedexViewModel.uiSingleTimeEvent.collect { event ->
            when (event) {
                is UiSingleTimeEvent.Navigate -> navController.navigate(event.route)
                else -> Unit
            }
        }
    }
    Scaffold(
        content = {padding ->
            ListContent(pokemonListItemEntities = pokemonListItemEntities, modifier = Modifier.padding(padding),
                onItemClick = { id, color -> pokedexViewModel.onEvent(PokedexEvent.OnPokemonClick(id, color))})
        }
    )
}