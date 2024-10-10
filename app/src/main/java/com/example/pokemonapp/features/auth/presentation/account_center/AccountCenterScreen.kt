package com.example.pokemonapp.features.auth.presentation.account_center

import com.example.pokemonapp.R
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import com.example.pokemonapp.features.auth.data.model.User
import com.example.pokemonapp.features.auth.data.model.UserData
import com.example.pokemonapp.navigation.NavState
import com.example.pokemonapp.navigation.Screen
import com.example.pokemonapp.ui.commonViews.AppBar

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun AccountCenterScreen(
    navState: NavState,
    restartApp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AccountCenterViewModel,
    onPickImage: () -> Unit,
    onTakePhoto: () -> Unit
) {
    val userData by viewModel.firebaseService.userData.collectAsState(UserData())
    val user by viewModel.firebaseService.user.collectAsState(User())

    Scaffold(topBar = { AppBar(navState, "Account Center", Screen.AccountCenterScreen.route, Screen.PokedexScreen.route) })
    {padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var showDialog by remember { mutableStateOf(false) }

            Box(modifier = Modifier.padding(top = 12.dp)) {
                ProfileImage(userData.image, onClick = {
                    showDialog = true
                }, modifier = Modifier.align(Alignment.Center))
                IconButton(onClick = {viewModel.deleteImage()}, modifier = Modifier.size(50.dp).align(Alignment.TopEnd)){
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Image", modifier = Modifier.size(50.dp))
                }
            }

            if(showDialog)
                ImageSelectionDialog(
                    onSelectFromGallery = {
                        onPickImage()
                        showDialog = false },
                    onTakePicture = {
                        onTakePhoto()
                        showDialog = false },
                    onDismiss = {showDialog = false}
                )

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp))

            DisplayNameCard(userData.displayName) { viewModel.onUpdateDisplayNameClick(it) }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp))

            Card(modifier = Modifier.card()) {
                Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                    if (!user.isAnonymous) {
                        Text(
                            text = String.format(stringResource(R.string.profile_email), user.email),
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                        )
                    }

                    // ⚠️This is for demonstration purposes only, it's not a common
                    // practice to show the unique ID or account provider of an account⚠️
                    Text(
                        text = String.format(stringResource(R.string.profile_uid), user.id),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    )

                }
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp))

            if (user.isAnonymous) {
                AccountCenterCard(stringResource(R.string.sign_in), Icons.Filled.Face, Modifier.card()) {
                    viewModel.onSignInClick(restartApp)
                }

                AccountCenterCard(stringResource(R.string.sign_up), Icons.Filled.AccountCircle, Modifier.card()) {
                    viewModel.onSignUpClick(restartApp)
                }
            } else {
                ExitAppCard { viewModel.onSignOutClick(restartApp) }
                RemoveAccountCard { viewModel.onDeleteAccountClick(restartApp) }
            }
        }
    }
}
