package com.example.pokemonapp.features.auth.data.service

import android.util.Log
import com.example.pokemonapp.features.auth.data.model.Favourite
import com.example.pokemonapp.features.auth.data.service.StorageServiceImpl.Companion.USER_DATA_COLLECTION
import com.example.pokemonapp.features.auth.domain.FavouriteService
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FavouriteServiceImpl @Inject constructor() : FavouriteService {

    override fun watchFavourite(userId: String): Flow<List<Favourite>> = callbackFlow {
        Log.d("LOLOLO", "watch favourite called")
        val favouritesCollection = Firebase.firestore
            .collection(USER_DATA_COLLECTION)
            .document(userId)
            .collection(FAVOURITES_COLLECTION)

        val listenerRegistration = favouritesCollection.addSnapshotListener { snapshots, error ->
            if (error != null) {
                Log.e("Firestore", "Error listening to favourites", error)
                close(error)
                return@addSnapshotListener
            }

            val favourites = snapshots?.documents?.mapNotNull { it.toObject(Favourite::class.java) }
            trySend(favourites ?: emptyList())
        }

        awaitClose { listenerRegistration.remove() }
    }

    override suspend fun addFavourite(userId: String, favourite: Favourite) {
        Firebase.firestore
            .collection(USER_DATA_COLLECTION)
            .document(userId)
            .collection(FAVOURITES_COLLECTION)
            .document(favourite.pokemonId) // Store each favourite under its pokemonId
            .set(favourite).await()
    }

    override suspend fun readAllFavourite(userId: String): List<Favourite> {
        val snapshots = Firebase.firestore
            .collection(USER_DATA_COLLECTION)
            .document(userId)
            .collection(FAVOURITES_COLLECTION)
            .get().await()

        return snapshots.documents.mapNotNull { it.toObject<Favourite>() }
    }

    override suspend fun removeFavourite(userId: String, pokemonId: String) {
        Firebase.firestore
            .collection(USER_DATA_COLLECTION)
            .document(userId)
            .collection(FAVOURITES_COLLECTION)
            .document(pokemonId) // Remove based on pokemonId
            .delete().await()
    }

    companion object {
        private const val FAVOURITES_COLLECTION = "Favourites"
    }
}