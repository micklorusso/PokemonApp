package com.example.pokemonapp.features.auth.domain

import com.example.pokemonapp.features.auth.data.model.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUser: Flow<User>
    val currentUserId: String
    fun hasUser(): Boolean
    fun getUserProfile(): User
    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun signInWithGoogle(idToken: String)
    suspend fun linkAccountWithGoogle(idToken: String)
    suspend fun signOut()
    suspend fun deleteAccount()
}
