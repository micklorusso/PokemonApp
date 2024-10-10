package com.example.pokemonapp.features.auth.presentation.account_center

import android.content.Context
import android.net.Uri
import com.example.pokemonapp.features.auth.AuthViewModel
import com.example.pokemonapp.features.auth.domain.AccountService
import com.example.pokemonapp.features.auth.domain.FirebaseService
import com.example.pokemonapp.features.auth.domain.StorageService
import com.example.pokemonapp.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class AccountCenterViewModel @Inject constructor(
    val accountService: AccountService,
    private val storageService: StorageService,
    val firebaseService: FirebaseService
): AuthViewModel() {

    fun onUpdateDisplayNameClick(newDisplayName: String) {
        launchCatching {
            storageService.updateUserData(firebaseService.userData.first().copy(displayName = newDisplayName))
        }
    }

    fun onSignInClick(openScreen: (String) -> Unit) = openScreen(Screen.SignInScreen.route)

    fun onSignUpClick(openScreen: (String) -> Unit) = openScreen(Screen.SignUpScreen.route)

    fun onSignOutClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.signOut()
            restartApp(Screen.SplashScreen.route)
        }
    }

    fun onDeleteAccountClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.deleteAccount()
            storageService.deleteUserData(firebaseService.userData.first().userId)
            restartApp(Screen.SplashScreen.route)
        }
    }

    fun uploadImageToLocalAndFirestore(context: Context, imageUri: Uri?) {
        imageUri?.let { uri ->
            val localImagePath = saveImageToLocalStorage(context, uri)
            if (localImagePath != null) {
                // Save the local image path to Firestore
                launchCatching {
                    storageService.updateUserData(firebaseService.userData.first().copy(image = localImagePath))
                }
            } else {
                // Handle failure to save locally
            }
        }
    }

    private fun saveImageToLocalStorage(context: Context, imageUri: Uri): String? {
        try {
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val file = File(context.filesDir, "${System.currentTimeMillis()}.jpg")
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            return file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun deleteImage(){
        launchCatching {
            if(deleteImageFromLocalStorage(firebaseService.userData.first().image)){
                storageService.updateUserData(firebaseService.userData.first().copy(image = ""))
            }
        }
    }

    private fun deleteImageFromLocalStorage(imagePath: String): Boolean {
        val file = File(imagePath)
        return if (file.exists()) {
            file.delete()
        } else {
            false
        }
    }

}