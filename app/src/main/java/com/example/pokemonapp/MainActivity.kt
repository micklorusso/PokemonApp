package com.example.pokemonapp

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.remember
import androidx.core.content.FileProvider
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import com.example.pokemonapp.features.auth.presentation.account_center.AccountCenterViewModel
import com.example.pokemonapp.navigation.NavState
import com.example.pokemonapp.navigation.SetupNavGraph
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import com.example.pokemonapp.util.Constants.AUTH_PORT
import com.example.pokemonapp.util.Constants.FIRESTORE_PORT
import com.example.pokemonapp.util.Constants.LOCALHOST
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val accountCenterViewModel: AccountCenterViewModel by viewModels()

    // Activity result launchers
    private lateinit var pickImageLauncher: ActivityResultLauncher<String>
    private lateinit var takePictureLauncher: ActivityResultLauncher<Uri>
    private var currentPhotoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configure Firebase services
        configureFirebaseServices()
        enableEdgeToEdge()

        // Initialize Activity Result Launchers
        initializeActivityResultLaunchers()

        setContent {
            PokemonAppTheme {
                val navController = rememberNavController()
                val navState = remember(navController) {
                    NavState(navController)
                }
                SetupNavGraph(navController = navController, navState, accountCenterViewModel, onTakePhoto = {
                    takePicture()
                }, onPickImage = { openImagePicker()})
            }
        }
    }

    // Function to initialize Activity Result Launchers
    private fun initializeActivityResultLaunchers() {
        // Register for gallery image picking
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                accountCenterViewModel.uploadImageToLocalAndFirestore(this, it)
            }
        }

        // Register for camera photo capture
        takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
            if (success) {
                currentPhotoUri?.let {
                    accountCenterViewModel.uploadImageToLocalAndFirestore(this, it)
                }
            }
        }
    }

    // Function to open the gallery picker
    private fun openImagePicker() {
        pickImageLauncher.launch("image/*") // This MIME type filters for images
    }

    // Function to open the camera and capture an image
    private fun takePicture() {
        val imageFile = createImageFile()
        val imageUri = FileProvider.getUriForFile(
            this,
            "${BuildConfig.APPLICATION_ID}.fileprovider",
            imageFile
        )
        currentPhotoUri = imageUri
        takePictureLauncher.launch(imageUri)
    }

    // Helper function to create a file for the captured image
    private fun createImageFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timestamp}_", ".jpg", storageDir)
    }

    private fun configureFirebaseServices() {
        if (BuildConfig.DEBUG) {
            Firebase.auth.useEmulator(LOCALHOST, AUTH_PORT)
            Firebase.firestore.useEmulator(LOCALHOST, FIRESTORE_PORT)
        }
    }
}
