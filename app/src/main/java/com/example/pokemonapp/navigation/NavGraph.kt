package com.example.pokemonapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.ExperimentalPagingApi
import com.example.pokemonapp.features.pokedex.presentation.pokedex.ui.PokedexScreen
import com.example.pokemonapp.features.pokedex.presentation.pokemonDetail.ui.PokemonDetailScreen

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
        composable(
            route = Screen.PokemonDetailScreen.route + "/{id}&{color}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                },
                navArgument(name = "color") {
                    type = NavType.StringType
                }
            )
        ) {
                backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            val color = backStackEntry.arguments?.getString("color")
            PokemonDetailScreen(id!!, color!!, navController)
        }
    }
}