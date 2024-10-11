package com.example.pokemonapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.ExperimentalPagingApi
import com.example.pokemonapp.features.auth.presentation.account_center.AccountCenterScreen
import com.example.pokemonapp.features.auth.presentation.account_center.AccountCenterViewModel
import com.example.pokemonapp.features.auth.presentation.settings.ui.SettingsScreen
import com.example.pokemonapp.features.auth.presentation.settings.viewModel.SettingsViewModel
import com.example.pokemonapp.features.auth.presentation.sign_in.SignInScreen
import com.example.pokemonapp.features.auth.presentation.sign_up.SignUpScreen
import com.example.pokemonapp.features.auth.presentation.splash.SplashScreen
import com.example.pokemonapp.features.pokedex.presentation.favourite.ui.FavouriteScreen
import com.example.pokemonapp.features.pokedex.presentation.favourite.viewModel.FavouriteViewModel
import com.example.pokemonapp.features.pokedex.presentation.pokedex.ui.PokedexScreen
import com.example.pokemonapp.features.pokedex.presentation.pokedex.viewModel.PokedexViewModel
import com.example.pokemonapp.features.pokedex.presentation.pokemonDetail.ui.PokemonDetailScreen
import com.example.pokemonapp.features.pokedex.presentation.search.ui.SearchScreen
import com.example.pokemonapp.features.pokedex.presentation.search.viewModel.SearchViewModel

@ExperimentalPagingApi
@Composable
fun SetupNavGraph(navController: NavHostController, navState: NavState, accountCenterViewModel: AccountCenterViewModel,
                  onPickImage: () -> Unit,
                  onTakePhoto: () -> Unit,
                  settingsViewModel: SettingsViewModel) {
    val pokedexViewModel = hiltViewModel<PokedexViewModel>()
    val favouriteViewModel = hiltViewModel<FavouriteViewModel>()
    val searchViewModel = hiltViewModel<SearchViewModel>()
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(route = Screen.PokedexScreen.route){
            PokedexScreen(navController = navController, pokedexViewModel)
        }
        composable(
            route = Screen.PokemonDetailScreen.route + "/{id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                },
            )
        ) {
                backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            PokemonDetailScreen(id!!, navController)
        }

        composable(Screen.SignInScreen.route) {
            SignInScreen(openAndPopUp = { route, popUp -> navState.navigateAndPopUp(route, popUp) })
        }

        composable(Screen.SignUpScreen.route) {
            SignUpScreen(openAndPopUp = { route, popUp -> navState.navigateAndPopUp(route, popUp) }, navState= navState)
        }

        composable(Screen.SplashScreen.route) {
            SplashScreen(openAndPopUp = { route, popUp -> navState.navigateAndPopUp(route, popUp) })
        }

        composable(Screen.AccountCenterScreen.route) {
            AccountCenterScreen(navState, viewModel = accountCenterViewModel,
                restartApp = { route -> navState.clearAndNavigate(route) }, onPickImage = {onPickImage()},
                onTakePhoto = {onTakePhoto()})
        }
        composable(Screen.FavouriteScreen.route){
            FavouriteScreen(navController, navState, favouriteViewModel)
        }
        composable(Screen.FilterScreen.route){
            SearchScreen(navState, navController, searchViewModel)
        }
        composable(Screen.SettingsScreen.route){
            SettingsScreen(navState, settingsViewModel = settingsViewModel)
        }

    }
}