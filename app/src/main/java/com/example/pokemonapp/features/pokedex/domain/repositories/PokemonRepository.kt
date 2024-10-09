package com.example.pokemonapp.features.pokedex.domain.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokemonapp.features.pokedex.data.datasources.api.PokemonApi
import com.example.pokemonapp.features.pokedex.data.datasources.localDatabase.PokemonDatabase
import com.example.pokemonapp.features.pokedex.data.paging.PokemonRemoteMediator
import com.example.pokemonapp.features.pokedex.domain.entities.PokemonDetailEntity
import com.example.pokemonapp.features.pokedex.domain.entities.PokemonListItemEntity
import com.example.pokemonapp.util.Constants.ITEMS_PER_PAGE
import com.example.pokemonapp.util.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class PokemonRepository @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val pokemonDatabase: PokemonDatabase
) {

    fun getPokedex(): Flow<PagingData<PokemonListItemEntity>> {
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

    suspend fun getPokemonDetail(id: Int): Resource<PokemonDetailEntity> = coroutineScope {
        return@coroutineScope try {
            val pokemonDetailDeferred = async { pokemonApi.getPokemonDetail(id) }
            val pokemonSpeciesDeferred = async { pokemonApi.getPokemonSpecies(id) }

            val pokemonDetailResponse = pokemonDetailDeferred.await()
            val pokemonSpeciesResponse = pokemonSpeciesDeferred.await()

            val pokemonDetail = pokemonDetailResponse.body()
            val pokemonSpecies = pokemonSpeciesResponse.body()

            if (pokemonDetailResponse.isSuccessful && pokemonDetail != null &&
                pokemonSpeciesResponse.isSuccessful && pokemonSpecies != null
            ) {
                Resource.Success(
                    PokemonDetailEntity.pokemonDetailEntityMapper(
                        pokemonDetail,
                        pokemonSpecies
                    )
                )
            } else {
                Resource.Error(pokemonDetailResponse.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "GET /beers/ale/id: an error occurred")
        }

    }
}