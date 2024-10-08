package com.example.pokemonapp.features.pokedex.data.model.pokemonList

import com.google.gson.annotations.SerializedName

data class PokemonListItem(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
){
    companion object{
        fun getIdFromUrl(url: String): Int{
            return url.trimEnd('/').split("/").last().toInt()
        }
    }
}

