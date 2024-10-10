package com.example.pokemonapp.features.auth.data.service

import android.net.Uri
import android.util.Log
import com.example.pokemonapp.features.auth.data.model.UserData
import com.example.pokemonapp.features.auth.domain.StorageService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow


class StorageServiceImpl @Inject constructor(): StorageService {

    override fun watchUserData(userId: String): Flow<UserData?> = callbackFlow {
        val documentReference = Firebase.firestore.collection(USER_DATA_COLLECTION).document(userId)

        val listenerRegistration = documentReference.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                // Document exists, send the data
                trySend(snapshot.toObject(UserData::class.java))
            } else {
                // Document doesn't exist, create default data
                val defaultData = UserData(userId = userId)
                documentReference.set(defaultData)
                trySend(defaultData)
            }
        }

        awaitClose { listenerRegistration.remove() }
    }

    override suspend fun createUserData(userData: UserData) {
        Firebase.firestore
            .collection(USER_DATA_COLLECTION)
            .add(userData).await()
    }

    override suspend fun readUserData(userId: String): UserData? {
        return Firebase.firestore
            .collection(USER_DATA_COLLECTION)
            .document(userId).get().await().toObject<UserData>()
    }

    override suspend fun updateUserData(userData: UserData) {
        Firebase.firestore
            .collection(USER_DATA_COLLECTION)
            .document(userData.userId).set(userData).await()
    }

    override suspend fun deleteUserData(userId: String) {
        Firebase.firestore
            .collection(USER_DATA_COLLECTION)
            .document(userId).delete().await()
    }

    companion object {
        const val USER_DATA_COLLECTION = "userData"
    }

}