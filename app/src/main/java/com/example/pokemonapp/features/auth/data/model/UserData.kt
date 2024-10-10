package com.example.pokemonapp.features.auth.data.model

import com.google.firebase.firestore.DocumentId

data class UserData (
    @DocumentId val userId: String = "",
    val displayName: String = "",
    val age: Int? = null,
    val address: String = "",
    val image: String = "",
)