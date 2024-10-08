package com.example.pokemonapp.features.pokedex.data.model.pokemonList


import com.google.gson.annotations.SerializedName

data class PokemonListModel(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: List<PokemonListItem>
)