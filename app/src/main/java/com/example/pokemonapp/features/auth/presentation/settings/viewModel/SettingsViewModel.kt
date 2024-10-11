package com.example.pokemonapp.features.auth.presentation.settings.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsDataStore: SettingsDataStore): ViewModel(){

    val isDarkThemeEnabled = mutableStateOf(false)
    val isNotificationsEnabled = mutableStateOf(false)

    init {
        viewModelScope.launch {
            settingsDataStore.isDarkTheme.collect { darkTheme ->
                isDarkThemeEnabled.value = darkTheme
            }

            settingsDataStore.notificationsEnabled.collect { notifications ->
                isNotificationsEnabled.value = notifications
            }
        }
    }

    fun onEvent(event: SettingsEvent){
        when(event){
            is SettingsEvent.OnThemeToggle -> {
                val newValue = event.isDarkThemeEnabled
                isDarkThemeEnabled.value = newValue
                viewModelScope.launch {
                    settingsDataStore.setDarkTheme(newValue)
                }
            }
            is SettingsEvent.OnNotificationToggle -> {
                val newValue = event.isNotificationEnabled
                isNotificationsEnabled.value = newValue
                viewModelScope.launch {
                    settingsDataStore.setNotificationsEnabled(newValue)
                }
            }
        }
    }

}