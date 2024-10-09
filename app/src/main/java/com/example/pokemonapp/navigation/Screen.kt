package com.example.pokemonapp.navigation

sealed class Screen(val route: String){
    data object PokedexScreen: Screen("pokedex_screen")
    data object PokemonDetailScreen: Screen("pokemon_detail_screen")
}
