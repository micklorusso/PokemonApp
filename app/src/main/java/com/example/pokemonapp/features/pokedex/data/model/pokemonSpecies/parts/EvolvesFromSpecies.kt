package com.example.pokemonapp.features.pokedex.data.model.pokemonSpecies


import com.google.gson.annotations.SerializedName

data class EvolvesFromSpecies(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)