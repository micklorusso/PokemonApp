package com.example.pokemonapp.navigation

sealed class Screen(val route: String){
    data object PokedexScreen: Screen("pokedex_screen")
    data object PokemonDetailScreen: Screen("pokemon_detail_screen")
    data object SplashScreen: Screen("splash_screen")
    data object SignInScreen: Screen("sign_in_screen")
    data object SignUpScreen: Screen("sign_up_screen")
    data object AccountCenterScreen: Screen("account_center_screen")
    data object FavouriteScreen: Screen("favourite_screen")
}
