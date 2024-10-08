package com.example.pokemonapp.features.pokedex.data.model.pokemonSpecies


import com.google.gson.annotations.SerializedName

data class FormDescription(
    @SerializedName("description")
    val description: String,
    @SerializedName("language")
    val language: Language
)