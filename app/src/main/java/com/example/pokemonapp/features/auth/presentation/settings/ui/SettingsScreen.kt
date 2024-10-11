package com.example.pokemonapp.features.auth.presentation.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.wear.compose.material.Switch
import com.example.pokemonapp.features.auth.presentation.settings.viewModel.SettingsEvent
import com.example.pokemonapp.features.auth.presentation.settings.viewModel.SettingsViewModel
import com.example.pokemonapp.navigation.NavState
import com.example.pokemonapp.navigation.Screen
import com.example.pokemonapp.ui.commonViews.AppBar


@Composable
fun SettingsScreen(navState: NavState,
                   settingsViewModel: SettingsViewModel){

    Scaffold(topBar = { AppBar(navState, "Settings", Screen.AccountCenterScreen.route, Screen.PokedexScreen.route) })
    {padding ->
        Column(
            modifier = Modifier.padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Notification Switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Enable Notifications")
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = settingsViewModel.isNotificationsEnabled.value,
                    onCheckedChange = {
                        settingsViewModel.onEvent(SettingsEvent.OnNotificationToggle(it))
                    }
                )
            }

            // Theme Switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Dark Theme")
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = settingsViewModel.isDarkThemeEnabled.value,
                    onCheckedChange = {
                        settingsViewModel.onEvent(SettingsEvent.OnThemeToggle(it))
                    }
                )
            }
        }
    }
}