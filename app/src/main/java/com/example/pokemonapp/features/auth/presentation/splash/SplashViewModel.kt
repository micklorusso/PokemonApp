package com.example.pokemonapp.features.auth.presentation.splash

import androidx.lifecycle.ViewModel
import com.example.pokemonapp.features.auth.domain.AccountService
import com.example.pokemonapp.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        if (accountService.hasUser()) openAndPopUp(Screen.PokedexScreen.route, Screen.SplashScreen.route)
        else openAndPopUp(Screen.SignInScreen.route, Screen.SplashScreen.route)
    }
}