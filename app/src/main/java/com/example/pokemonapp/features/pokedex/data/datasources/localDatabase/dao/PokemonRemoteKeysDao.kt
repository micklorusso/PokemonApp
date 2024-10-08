package com.example.pokemonapp.features.pokedex.data.datasources.localDatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokemonapp.features.pokedex.data.model.PokemonRemoteKeys
import com.example.pokemonapp.util.Constants

@Dao
interface PokemonRemoteKeysDao {
    @Query("SELECT * FROM ${Constants.POKEMON_REMOTE_KEYS_TABLE} WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): PokemonRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<PokemonRemoteKeys>)

    @Query("DELETE FROM ${Constants.POKEMON_REMOTE_KEYS_TABLE}")
    suspend fun deleteAllRemoteKeys()
}