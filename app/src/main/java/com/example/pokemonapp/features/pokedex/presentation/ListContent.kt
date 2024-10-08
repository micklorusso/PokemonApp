package com.example.pokemonapp.features.pokedex.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.example.pokemonapp.features.pokedex.domain.entities.PokemonListItemEntity
import java.util.Locale

@Composable
fun ListContent(pokemonListItemEntities: LazyPagingItems<PokemonListItemEntity>, modifier: Modifier) {

    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(12.dp)
    ) {
        item(span = { GridItemSpan(this.maxLineSpan)}){
            Text("Pokedex", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
                modifier = Modifier.padding(12.dp))
        }
        items(pokemonListItemEntities.itemCount) { index ->
            pokemonListItemEntities[index]?.let { PokemonItem(pokemon = it) }
        }
    }
}


@Composable
fun PokemonItem(pokemon: PokemonListItemEntity) {
    val pokemonColor = Color(android.graphics.Color.parseColor(pokemon.color))
    val textColor = if (pokemonColor.luminance() > 0.5f) Color.Black else Color.White
    Card(modifier = Modifier
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
                    TypeBadge(type, textColor)
                } }
                AsyncImage(model = pokemon.imageUrl, modifier = Modifier.weight(1f), contentDescription = "${pokemon.name} image")
            }
        }
    }
}

@Composable
fun TypeBadge(type: String, textColor: Color) {
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 6.dp)
            .background(
                color = textColor.copy(alpha = 0.2f),
                shape = RoundedCornerShape(50) // Fully rounded corners
            )
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = type,
            color = textColor,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}