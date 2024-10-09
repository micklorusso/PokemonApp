package com.example.pokemonapp.features.pokedex.presentation.pokemonDetail.ui.detailCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pokemonapp.features.pokedex.domain.entities.PokemonDetailEntity

@Composable
fun AboutCard(pokemonDetail: PokemonDetailEntity) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        AboutRow("Species", pokemonDetail.species)
        AboutRow("Height", pokemonDetail.height)
        AboutRow("Weight", pokemonDetail.weight)
        AboutRow("Abilities", pokemonDetail.abilities.joinToString(separator = ", "))
        Text(
            "Breeding",
            Modifier.padding(vertical = 8.dp),
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gender", modifier = Modifier.weight(1f))
            Row(modifier = Modifier.weight(1f)) {
                Icon(imageVector = Icons.Filled.Male, contentDescription = "Male")
                Text(pokemonDetail.gender["male"].toString())
            }
            Row(modifier = Modifier.weight(1f)) {
                Icon(imageVector = Icons.Filled.Female, contentDescription = "Female")
                Text(pokemonDetail.gender["female"].toString())
            }
            AboutRow("Egg Groups", pokemonDetail.eggGroups.joinToString(", "))
        }
    }
}

@Composable
fun AboutRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(text = label, modifier = Modifier.weight(1f))
        Text(text = value, modifier = Modifier.weight(1f))
    }
}