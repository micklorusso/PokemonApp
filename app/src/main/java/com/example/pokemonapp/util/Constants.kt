package com.example.pokemonapp.util

object Constants {
    const val BASE_URL = "https://pokeapi.co/api/v2/"

    const val POKEMON_DATABASE = "pokemon_database"
    const val POKEMON_TABLE = "pokemon_table"
    const val POKEMON_REMOTE_KEYS_TABLE = "pokemon_remote_keys_table"
    const val ITEMS_PER_PAGE =  20

    val POKEDEX_COLOR_MAP: Map<String, String> = mapOf(
        "red" to "#FF0000",
        "blue" to "#0000FF",
        "yellow" to "#FFFF00",
        "green" to "#00FF00",
        "black" to "#000000",
        "brown" to "#A52A2A",
        "purple" to "#800080",
        "gray" to "#808080",
        "white" to "#FFFFFF",
        "pink" to "#FFC0CB"
    )

    const val LOCALHOST = "10.0.2.2"
    const val AUTH_PORT = 9099
    const val FIRESTORE_PORT = 8080
    const val STORAGE_PORT = 9199
    const val UNEXPECTED_CREDENTIAL = "Unexpected type of credential"
}