package com.example.pokemonapp.features.pokedex.data.datasources.localDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokemonapp.features.pokedex.data.datasources.localDatabase.dao.PokemonDao
import com.example.pokemonapp.features.pokedex.data.datasources.localDatabase.dao.PokemonRemoteKeysDao
import com.example.pokemonapp.features.pokedex.data.model.PokemonRemoteKeys
import com.example.pokemonapp.features.pokedex.data.model.pokemonList.PokemonListItem
import com.example.pokemonapp.features.pokedex.domain.entities.PokemonListItemEntity
import com.example.pokemonapp.util.Converters

@Database(entities = [PokemonListItemEntity::class, PokemonRemoteKeys::class], version = 1)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
    abstract fun pokemonRemoteKeysDao(): PokemonRemoteKeysDao

}