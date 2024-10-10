package com.example.pokemonapp.features.auth.data.model

import com.example.pokemonapp.features.pokedex.domain.entities.PokemonDetailEntity
import com.example.pokemonapp.features.pokedex.domain.entities.PokemonListItemEntity
import com.google.firebase.firestore.DocumentId

data class Favourite (
    @DocumentId val pokemonId: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val color: String = "",
    val types: List<String> = emptyList()
){
    companion object{
        fun toFavourite(pokemonDetail: PokemonDetailEntity): Favourite{
            return Favourite(pokemonDetail.id.toString(), pokemonDetail.name,
                pokemonDetail.sprite, pokemonDetail.color, pokemonDetail.types)
        }
    }
}