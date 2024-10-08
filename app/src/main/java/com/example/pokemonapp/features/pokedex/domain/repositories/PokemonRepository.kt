package com.example.pokemonapp.features.pokedex.domain.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokemonapp.features.pokedex.data.datasources.api.PokemonApi
import com.example.pokemonapp.features.pokedex.data.datasources.localDatabase.PokemonDatabase
import com.example.pokemonapp.features.pokedex.data.paging.PokemonRemoteMediator
import com.example.pokemonapp.features.pokedex.domain.entities.PokemonListItemEntity
import com.example.pokemonapp.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class PokemonRepository @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val pokemonDatabase: PokemonDatabase
) {

    fun getPokemon(): Flow<PagingData<PokemonListItemEntity>> {
        val pagingSourceFactory = { pokemonDatabase.pokemonDao().getAllPokemon() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = PokemonRemoteMediator(
                pokemonApi = pokemonApi,
                pokemonDatabase = pokemonDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

}