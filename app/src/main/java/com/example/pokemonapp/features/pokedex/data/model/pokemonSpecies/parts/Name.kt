package com.example.pokemonapp.features.pokedex.data.model.pokemonSpecies


import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("language")
    val language: Language,
    @SerializedName("name")
    val name: String
)