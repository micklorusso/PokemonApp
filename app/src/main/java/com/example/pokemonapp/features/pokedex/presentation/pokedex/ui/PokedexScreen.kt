package com.example.pokemonapp.features.pokedex.presentation.pokedex.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokemonapp.features.auth.data.model.User
import com.example.pokemonapp.features.pokedex.presentation.pokedex.viewModel.PokedexEvent
import com.example.pokemonapp.features.pokedex.presentation.pokedex.viewModel.PokedexViewModel
import com.example.pokemonapp.util.UiSingleTimeEvent


@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalPagingApi
@Composable
fun PokedexScreen(
    navController: NavHostController,
    pokedexViewModel: PokedexViewModel
) {
    val user = pokedexViewModel.firebaseService.user.collectAsState(User())
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
        topBar = {
            TopAppBar(title = { Text("Pokedex")}, actions = {
                HamburgerMenu(navigateToProfile = {
                    pokedexViewModel.onEvent(PokedexEvent.OnProfileClick)
                },
                    navigateToSettings = {
                        pokedexViewModel.onEvent(PokedexEvent.OnSettingsClick)
                    },
                    navigateToFavourites = {
                        pokedexViewModel.onEvent(PokedexEvent.OnFavouritesClick)
                    },
                    user.value.isAnonymous
                )
            })
        },
        content = {padding ->
            ListContent(pokemonListItemEntities = pokemonListItemEntities, modifier = Modifier.padding(padding),
                onItemClick = { id -> pokedexViewModel.onEvent(PokedexEvent.OnPokemonClick(id))})
        }
    )
}