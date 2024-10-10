package com.example.pokemonapp.features.auth.domain

import com.example.pokemonapp.features.auth.data.model.UserData
import kotlinx.coroutines.flow.Flow

interface StorageService {
    fun watchUserData(userId: String): Flow<UserData?>
    suspend fun createUserData(userData: UserData)
    suspend fun readUserData(userId: String): UserData?
    suspend fun updateUserData(userData: UserData)
    suspend fun deleteUserData(userId: String)
}