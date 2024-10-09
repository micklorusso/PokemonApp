package com.example.pokemonapp.features.pokedex.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.pokemonapp.features.pokedex.data.datasources.api.PokemonApi
import com.example.pokemonapp.features.pokedex.data.datasources.localDatabase.PokemonDatabase
import com.example.pokemonapp.features.pokedex.data.model.PokemonRemoteKeys
import com.example.pokemonapp.features.pokedex.data.model.pokemonList.PokemonListItem
import com.example.pokemonapp.features.pokedex.domain.entities.PokemonListItemEntity
import com.example.pokemonapp.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

@ExperimentalPagingApi
class PokemonRemoteMediator(
    private val pokemonApi: PokemonApi,
    private val pokemonDatabase: PokemonDatabase
) : RemoteMediator<Int, PokemonListItemEntity>() {

    private val pokemonDao = pokemonDatabase.pokemonDao()
    private val pokemonRemoteKeysDao = pokemonDatabase.pokemonRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonListItemEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = pokemonApi.getPokemonList(page = currentPage, perPage = ITEMS_PER_PAGE).body()?.results
            val endOfPaginationReached = response?.isEmpty()?: true

            val pokemonListItemEntities = coroutineScope {
                response?.map { pokemonListItem ->
                    async {
                        val id = PokemonListItem.getIdFromUrl(pokemonListItem.url)
                        val pokemonDetail = async {pokemonApi.getPokemonDetail(id)}.await().body()!!
                        val pokemonSpecies = async{pokemonApi.getPokemonSpecies(id)}.await().body()!!
                        PokemonListItemEntity.pokemonListItemEntityMapper(pokemonDetail, pokemonSpecies)
                    }
                }?.awaitAll() ?: emptyList()
            }

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            pokemonDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pokemonDao.deleteAllPokemon()
                    pokemonRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = pokemonListItemEntities.map { pokemon ->
                    PokemonRemoteKeys(
                        id = pokemon.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                pokemonRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                pokemonDao.addPokemon(pokemon = pokemonListItemEntities)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            Log.d("PokemonRemoteMediator", e.toString())
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PokemonListItemEntity>
    ): PokemonRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                pokemonRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PokemonListItemEntity>
    ): PokemonRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { pokemon ->
                pokemonRemoteKeysDao.getRemoteKeys(id = pokemon.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, PokemonListItemEntity>
    ): PokemonRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { pokemon ->
                pokemonRemoteKeysDao.getRemoteKeys(id = pokemon.id)
            }
    }

}