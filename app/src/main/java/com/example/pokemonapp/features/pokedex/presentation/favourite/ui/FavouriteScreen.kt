package com.example.pokemonapp.features.pokedex.presentation.favourite.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.ExperimentalPagingApi
import com.example.pokemonapp.features.auth.data.model.UserData
import com.example.pokemonapp.features.pokedex.presentation.favourite.viewModel.FavouriteEvent
import com.example.pokemonapp.features.pokedex.presentation.favourite.viewModel.FavouriteViewModel
import com.example.pokemonapp.features.pokedex.presentation.pokedex.viewModel.PokedexEvent
import com.example.pokemonapp.navigation.NavState
import com.example.pokemonapp.navigation.Screen
import com.example.pokemonapp.ui.commonViews.AppBar
import com.example.pokemonapp.util.UiSingleTimeEvent


@Composable
fun FavouriteScreen(
    navController: NavController,
    navState: NavState,
    favouriteViewModel: FavouriteViewModel
) {
    val favourite by favouriteViewModel.firebaseService.favourite.collectAsState(emptyList())
    LaunchedEffect(key1 = true) {
        favouriteViewModel.uiSingleTimeEvent.collect { event ->
            when (event) {
                is UiSingleTimeEvent.Navigate -> navController.navigate(event.route)
                else -> Unit
            }
        }
    }

    Scaffold(topBar = { AppBar(navState, "Favourites", Screen.FavouriteScreen.route, Screen.PokedexScreen.route) },
        content = {padding ->
            ListFavourites(favourite = favourite, modifier = Modifier.padding(padding),
                onItemClick = { id -> favouriteViewModel.onEvent(FavouriteEvent.OnPokemonClick(id))})
        }
    )
}