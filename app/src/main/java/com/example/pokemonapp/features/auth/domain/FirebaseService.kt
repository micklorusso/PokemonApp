package com.example.pokemonapp.features.auth.domain

import com.example.pokemonapp.features.auth.data.model.Favourite
import com.example.pokemonapp.features.auth.data.model.User
import com.example.pokemonapp.features.auth.data.model.UserData
import kotlinx.coroutines.flow.Flow


interface FirebaseService {
    val user: Flow<User>
    val userData: Flow<UserData>
    val favourite: Flow<List<Favourite>>
}