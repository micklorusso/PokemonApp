package com.example.pokemonapp.features.pokedex.presentation.pokemonDetail.ui.screenSection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.pokemonapp.features.pokedex.domain.entities.PokemonDetailEntity
import com.example.pokemonapp.features.pokedex.presentation.common.TypeBadge
import java.util.Locale

@Composable
fun PokemonDisplaySection(modifier: Modifier, pokemonDetail: PokemonDetailEntity,
                          pokemonColor: Color, textColor: Color,
                          onBackClick: () -> Unit,
                          onHartClick: () -> Unit,
                          isFavourite: Boolean) {
    Box(modifier = modifier.background(pokemonColor)) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = {
                    onBackClick()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = textColor
                    )
                }
                IconButton(onClick = {
                    onHartClick()
                }) {
                    Icon(
                        imageVector = if(isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favourite",
                        tint = textColor
                    )
                }
            }
            Text(
                text = pokemonDetail.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                },
                modifier = Modifier.padding(bottom = 8.dp, start = 20.dp),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 40.sp, color = textColor)
            )
            Row {
                pokemonDetail.types.forEach { type ->
                    TypeBadge(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp),
                        type,
                        textColor,
                        fontSize = 20.sp
                    )
                }
            }
            Image(
                painter = rememberAsyncImagePainter(pokemonDetail.imageUrl),
                contentDescription = "${pokemonDetail.name} image",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .height(250.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}