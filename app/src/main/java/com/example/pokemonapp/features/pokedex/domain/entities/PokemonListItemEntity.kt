package com.example.pokemonapp.features.pokedex.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokemonapp.features.pokedex.data.model.pokemonDetail.PokemonDetailModel
import com.example.pokemonapp.features.pokedex.data.model.pokemonSpecies.PokemonSpeciesModel
import com.example.pokemonapp.util.Constants.POKEDEX_COLOR_MAP
import com.example.pokemonapp.util.Constants.POKEMON_TABLE

@Entity(tableName = POKEMON_TABLE)
data class PokemonListItemEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val types: List<String>,
    val imageUrl: String,
    val color: String
){
    companion object{
        fun pokemonListItemEntityMapper(pokemonDetailModel: PokemonDetailModel, pokemonSpeciesModel: PokemonSpeciesModel): PokemonListItemEntity{
            val types = mutableListOf<String>()
            for(type in pokemonDetailModel.types)
                types.add(type.type.name)
            return PokemonListItemEntity(
                pokemonDetailModel.id,
                pokemonDetailModel.name,
                types,
                pokemonDetailModel.sprites.frontDefault,
                POKEDEX_COLOR_MAP[pokemonSpeciesModel.color.name]?: "#FFFFFF"
                )
        }
    }
}


