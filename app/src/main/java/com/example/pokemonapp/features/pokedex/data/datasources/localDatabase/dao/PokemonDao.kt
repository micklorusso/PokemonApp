package com.example.pokemonapp.features.pokedex.data.datasources.localDatabase.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokemonapp.features.pokedex.domain.entities.PokemonListItemEntity
import com.example.pokemonapp.util.Constants.POKEMON_TABLE

@Dao
interface PokemonDao {

    @Query("SELECT * FROM $POKEMON_TABLE")
    fun getAllPokemon(): PagingSource<Int, PokemonListItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemon(pokemon: List<PokemonListItemEntity>)

    @Query("DELETE FROM $POKEMON_TABLE")
    suspend fun deleteAllPokemon()

}