package com.example.pokemonapp.features.pokedex.presentation.pokemonDetail.ui.screenSection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.pokemonapp.features.pokedex.domain.entities.PokemonDetailEntity
import com.example.pokemonapp.features.pokedex.presentation.pokemonDetail.ui.detailCard.AboutCard
import com.example.pokemonapp.features.pokedex.presentation.pokemonDetail.ui.detailCard.BaseStatsCard

@Composable
fun PokemonDetailSection(modifier: Modifier, pokemonDetail: PokemonDetailEntity) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("About", "Base Stats", "Evolution", "Moves")
        Box(
            modifier = modifier) {
            Column {
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    indicator = { tabPositions ->
                        val selectedTabPosition = tabPositions[selectedTab]
                        Box(
                            modifier = Modifier
                                .tabIndicatorOffset(selectedTabPosition)
                                .background(Color.Blue, shape = RectangleShape)
                                .height(2.dp)
                        )
                    }
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title) },
                            selected = selectedTab == index,
                            onClick = { selectedTab = index }
                        )
                    }
                }
                when (selectedTab) {
                    0 -> AboutCard(pokemonDetail)
                    1 -> BaseStatsCard(pokemonDetail)
                    2 -> Unit
                    3 -> Unit
                }
            }
    }
}