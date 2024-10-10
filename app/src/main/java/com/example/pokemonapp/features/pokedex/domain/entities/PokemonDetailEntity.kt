package com.example.pokemonapp.features.pokedex.domain.entities

import com.example.pokemonapp.features.pokedex.data.model.Stat
import com.example.pokemonapp.features.pokedex.data.model.pokemonDetail.PokemonDetailModel
import com.example.pokemonapp.features.pokedex.data.model.pokemonSpecies.PokemonSpeciesModel
import com.example.pokemonapp.util.Constants.POKEDEX_COLOR_MAP

data class PokemonDetailEntity (
    val id: Int,
    val name: String,
    val types: List<String>,
    val imageUrl: String,
    val sprite: String,
    val color: String,
    val species: String,
    val height: String,
    val weight: String,
    val abilities: List<String>,
    val gender: Map<String, Float>?,
    val eggGroups: List<String>,
    val stats: List<Stat>
){
    companion object{

        fun pokemonDetailEntityMapper(pokemonDetailModel: PokemonDetailModel, pokemonSpeciesModel: PokemonSpeciesModel): PokemonDetailEntity{
            val types = mutableListOf<String>()
            for(type in pokemonDetailModel.types)
                types.add(type.type.name)

            var species: String = ""
            for(genus in pokemonSpeciesModel.genera)
                if(genus.language.name == "en")
                    species = genus.genus

            val height = "${pokemonDetailModel.height.toFloat() * 10f} cm"
            val weight = "${pokemonDetailModel.weight.toFloat() / 10f} kg"

            val abilities = mutableListOf<String>()
            for(ability in pokemonDetailModel.abilities)
                abilities.add(ability.ability.name)

            var gender: Map<String, Float>? = emptyMap()
            if(pokemonSpeciesModel.genderRate == -1){
                gender = null
            }
            else {
                val femalePercentage: Float = (pokemonSpeciesModel.genderRate.toFloat() / 8f) * 100f
                val malePercentage: Float = 100f - femalePercentage
                gender =  mapOf("male" to malePercentage, "female" to femalePercentage)
            }

            val eggGroups = mutableListOf<String>()
            for(eggGroup in pokemonSpeciesModel.eggGroups)
                eggGroups.add(eggGroup.name)

            val stats = mutableListOf<Stat>()
            for(stat in pokemonDetailModel.stats)
                stats.add(stat)

            return PokemonDetailEntity(
                id = pokemonDetailModel.id,
                name = pokemonDetailModel.name,
                types = types,
                imageUrl = pokemonDetailModel.sprites.other.officialArtwork.frontDefault,
                sprite = pokemonDetailModel.sprites.frontDefault,
                color = POKEDEX_COLOR_MAP[pokemonSpeciesModel.color.name]?: "#FFFFFF",
                species = species,
                height = height,
                weight = weight,
                abilities = abilities,
                gender = gender,
                eggGroups = eggGroups,
                stats = stats
            )
        }
    }
}