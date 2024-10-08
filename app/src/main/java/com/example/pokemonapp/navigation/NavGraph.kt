package com.example.pokemonapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.ExperimentalPagingApi
import com.example.pokemonapp.features.pokedex.presentation.PokedexScreen

@ExperimentalPagingApi
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.PokedexScreen.route
    ) {
        composable(route = Screen.PokedexScreen.route){
            PokedexScreen(navController = navController)
        }
    }
}