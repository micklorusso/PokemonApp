package com.example.pokemonapp.features.pokedex.presentation.pokemonDetail.ui.detailCard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.pokemonapp.features.pokedex.domain.entities.PokemonDetailEntity
import kotlin.math.roundToInt

@Composable
fun BaseStatsCard(pokemonDetail: PokemonDetailEntity) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        var total = 0
        var max = 0
        pokemonDetail.stats.forEachIndexed{index, stat ->
            StatRow(stat.stat.name, stat.baseStat, color = if(index % 2 == 0) Color.Red else Color.Green)
            total += stat.baseStat
            max += 100
        }
        StatRow("Total", ((total.toFloat() / max.toFloat()) * 100f).roundToInt(), displayValue = total , color = Color.Blue)
    }
}

@Composable
fun StatRow(statName: String, statValue: Int, displayValue: Int? = null, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(text = statName, modifier = Modifier.weight(1f),  maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = displayValue?.toString() ?: statValue.toString(), modifier = Modifier.padding(horizontal = 8.dp))
        LinearProgressIndicator(
            progress = { statValue / 100f },
            modifier = Modifier
                .weight(1.5f)
                .height(8.dp),
            color = color,
        )
    }
}