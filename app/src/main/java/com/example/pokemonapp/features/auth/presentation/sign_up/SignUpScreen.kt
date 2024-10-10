package com.example.pokemonapp.features.auth.presentation.sign_up

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokemonapp.R
import com.example.pokemonapp.features.auth.presentation.account_center.AuthenticationButton
import com.example.pokemonapp.navigation.NavState
import com.example.pokemonapp.navigation.Screen
import com.example.pokemonapp.ui.commonViews.AppBar
import com.example.pokemonapp.ui.theme.Purple40

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
    navState: NavState
) {

    val context = LocalContext.current
    val errorMessage by viewModel.errorMessage

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.resetErrorMessage()
        }
    }

    Scaffold(topBar = { AppBar(navState, "Sign Up", Screen.SignUpScreen.route, Screen.SignInScreen.route)}) { innerPadding ->
        val email = viewModel.email.collectAsState()
        val password = viewModel.password.collectAsState()
        val confirmPassword = viewModel.confirmPassword.collectAsState()

        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.mipmap.auth_image),
                contentDescription = "Auth image",
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp, 4.dp)
                    .weight(0.5f)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            )

            Column(modifier = Modifier.weight(1f)) {

                OutlinedTextField(
                    singleLine = true,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp, 4.dp)
                        .border(
                            BorderStroke(width = 2.dp, color = Purple40),
                            shape = RoundedCornerShape(50)
                        ),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    value = email.value,
                    onValueChange = { viewModel.updateEmail(it) },
                    placeholder = { Text(stringResource(R.string.email)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email"
                        )
                    }
                )

                OutlinedTextField(
                    singleLine = true,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp, 4.dp)
                        .border(
                            BorderStroke(width = 2.dp, color = Purple40),
                            shape = RoundedCornerShape(50)
                        ),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    value = password.value,
                    onValueChange = { viewModel.updatePassword(it) },
                    placeholder = { Text(stringResource(R.string.password)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Email"
                        )
                    },
                    visualTransformation = PasswordVisualTransformation()
                )

                OutlinedTextField(
                    singleLine = true,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp, 4.dp)
                        .border(
                            BorderStroke(width = 2.dp, color = Purple40),
                            shape = RoundedCornerShape(50)
                        ),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    value = confirmPassword.value,
                    onValueChange = { viewModel.updateConfirmPassword(it) },
                    placeholder = { Text(stringResource(R.string.confirm_password)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Email"
                        )
                    },
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )

                Button(
                    onClick = { viewModel.onSignUpClick(openAndPopUp) },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp)
                ) {
                    Text(
                        text = stringResource(R.string.sign_up),
                        fontSize = 16.sp,
                        modifier = modifier.padding(0.dp, 6.dp)
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )

                AuthenticationButton(R.string.sign_up_with_google) { credential ->
                    viewModel.onSignUpWithGoogle(credential, openAndPopUp)
                }

            }
        }
    }
}
