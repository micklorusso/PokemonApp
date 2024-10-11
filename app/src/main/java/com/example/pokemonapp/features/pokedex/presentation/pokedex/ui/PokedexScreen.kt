package com.example.pokemonapp.features.pokedex.presentation.pokedex.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokemonapp.R
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
        floatingActionButton = {FloatingActionButton(
            onClick = {
                pokedexViewModel.onEvent(PokedexEvent.OnFilterClick)
            },
            containerColor = Color(108, 124, 216),
            modifier = Modifier.size(60.dp).clip(CircleShape)
        ) {
            val image = ImageBitmap.imageResource(id = R.drawable.filter)
            val invertColorMatrix = ColorMatrix(
                floatArrayOf(
                    -1f,  0f,  0f,  0f,  255f,  // Red
                    0f, -1f,  0f,  0f,  255f,  // Green
                    0f,  0f, -1f,  0f,  255f,  // Blue
                    0f,  0f,  0f,  1f,    0f   // Alpha leave unchanged
                )
            )
            Image(
                bitmap = image,
                contentDescription = "Filter",
                modifier = Modifier.padding(10.dp),
                colorFilter = ColorFilter.colorMatrix(invertColorMatrix)
            )
        }},
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