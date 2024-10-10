package com.example.pokemonapp.features.pokedex.presentation.favourite.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokemonapp.features.auth.data.model.Favourite
import com.example.pokemonapp.features.pokedex.presentation.common.TypeBadge
import com.example.pokemonapp.util.UtilityFunctions
import java.util.Locale

@Composable
fun ListFavourites(favourite: List<Favourite>, modifier: Modifier,
                onItemClick: (Int) -> Unit) {

    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(12.dp)
    ) {
        items(favourite.size) { index ->
            favourite[index].let { pokemon ->
                PokemonFavouriteItem(
                    pokemon = pokemon,
                    modifier = Modifier.clickable {onItemClick(pokemon.pokemonId.toInt()) }
                )
            }
        }
    }
}


@Composable
fun PokemonFavouriteItem(pokemon: Favourite, modifier: Modifier) {
    val pokemonColor = UtilityFunctions.hexToColor(pokemon.color)
    val textColor = UtilityFunctions.getTextColor(pokemonColor)
    Card(modifier = modifier
        .size(128.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = pokemonColor,
        )
    ) {
        Column {
            Text(
                pokemon.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                style = TextStyle(color = textColor, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(start = 20.dp, top = 6.dp))
            Row {
                Column { pokemon.types.forEach{ type ->
                    TypeBadge(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp),type, textColor)
                } }
                AsyncImage(model = pokemon.imageUrl, modifier = Modifier.weight(1f), contentDescription = "${pokemon.name} image")
            }
        }
    }
}