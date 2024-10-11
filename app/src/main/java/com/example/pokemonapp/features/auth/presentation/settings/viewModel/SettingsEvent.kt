package com.example.pokemonapp.features.auth.presentation.settings.viewModel

import com.example.pokemonapp.features.pokedex.presentation.search.viewModel.SearchEvent

sealed class SettingsEvent {
    data class OnThemeToggle(val isDarkThemeEnabled: Boolean): SettingsEvent()
    data class OnNotificationToggle(val isNotificationEnabled: Boolean): SettingsEvent()
}