package com.example.pokemonapp.features.pokedex.presentation.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokemonapp.R
import com.example.pokemonapp.features.auth.data.model.User
import com.example.pokemonapp.features.pokedex.presentation.pokedex.ui.HamburgerMenu
import com.example.pokemonapp.features.pokedex.presentation.pokedex.ui.ListContent
import com.example.pokemonapp.features.pokedex.presentation.pokedex.viewModel.PokedexEvent
import com.example.pokemonapp.features.pokedex.presentation.pokedex.viewModel.PokedexViewModel
import com.example.pokemonapp.features.pokedex.presentation.search.viewModel.SearchEvent
import com.example.pokemonapp.features.pokedex.presentation.search.viewModel.SearchViewModel
import com.example.pokemonapp.navigation.NavState
import com.example.pokemonapp.navigation.Screen
import com.example.pokemonapp.ui.commonViews.AppBar
import com.example.pokemonapp.util.UiSingleTimeEvent

@ExperimentalPagingApi
@Composable
fun SearchScreen(
    navState: NavState,
    navController: NavHostController,
    searchViewModel: SearchViewModel
) {
    val filteredItems = searchViewModel.filteredItems.collectAsLazyPagingItems()
    val searchQuery by searchViewModel.searchQuery.collectAsState()

    LaunchedEffect(key1 = true) {
        searchViewModel.uiSingleTimeEvent.collect { event ->
            when (event) {
                is UiSingleTimeEvent.Navigate -> navController.navigate(event.route)
                else -> Unit
            }
        }
    }
    Scaffold(
        topBar = { AppBar(navState, "Search", Screen.FavouriteScreen.route, Screen.PokedexScreen.route) },
        content = { padding ->
            Column {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchViewModel.onEvent(SearchEvent.OnSearchQueryChanged(it)) },
                    label = { Text("Search") },
                    modifier = Modifier.fillMaxWidth().padding(padding),
                    singleLine = true
                )
                ListContent(pokemonListItemEntities = filteredItems,
                    onItemClick = { id -> searchViewModel.onEvent(SearchEvent.OnPokemonClick(id)) })
            }
        }
    )
}