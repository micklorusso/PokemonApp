package com.example.pokemonapp.features.auth.domain


import com.example.pokemonapp.features.auth.data.model.Favourite
import kotlinx.coroutines.flow.Flow

interface FavouriteService {
    fun watchFavourite(userId: String): Flow<List<Favourite>?>
    suspend fun addFavourite(userId: String, favourite: Favourite)
    suspend fun readAllFavourite(userId: String): List<Favourite>
    suspend fun removeFavourite(userId: String, pokemonId: String)
}