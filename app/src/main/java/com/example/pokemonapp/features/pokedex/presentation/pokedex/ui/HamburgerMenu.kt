package com.example.pokemonapp.features.pokedex.presentation.pokedex.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun HamburgerMenu(navigateToProfile: () -> Unit, navigateToSettings: () -> Unit, navigateToFavourites: () -> Unit,
                  isAnonymous: Boolean) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.Menu, contentDescription = "Hamburger Menu")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(text = {Text("Profile")},
                onClick = {
                expanded = false
                navigateToProfile()
            })

            DropdownMenuItem(text = { Text("Settings")},
                onClick = {
                expanded = false
                navigateToSettings()
            })

            if(!isAnonymous){
                DropdownMenuItem(text = { Text("Favourites")},
                    onClick = {
                        expanded = false
                        navigateToFavourites()
                    })
            }
        }
    }
}