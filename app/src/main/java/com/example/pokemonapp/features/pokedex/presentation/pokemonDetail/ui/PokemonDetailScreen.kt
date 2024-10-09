package com.example.pokemonapp.features.pokedex.presentation.pokemonDetail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pokemonapp.features.pokedex.presentation.pokemonDetail.ui.screenSection.PokemonDetailSection
import com.example.pokemonapp.features.pokedex.presentation.pokemonDetail.ui.screenSection.PokemonDisplaySection
import com.example.pokemonapp.features.pokedex.presentation.pokemonDetail.viewModel.PokemonDetailEvent
import com.example.pokemonapp.features.pokedex.presentation.pokemonDetail.viewModel.PokemonDetailState
import com.example.pokemonapp.features.pokedex.presentation.pokemonDetail.viewModel.PokemonDetailViewModel
import com.example.pokemonapp.ui.commonViews.LoadingIndicator
import com.example.pokemonapp.util.UiSingleTimeEvent
import com.example.pokemonapp.util.UtilityFunctions

@Composable
fun PokemonDetailScreen(
    id: Int,
    color: String,
    navController: NavHostController,
    pokemonDetailViewModel: PokemonDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(id) {
        pokemonDetailViewModel.getPokemonDetail(id)
    }

    val state = pokemonDetailViewModel.state.collectAsState(initial = PokemonDetailState.Empty)

    when (state.value) {
        is PokemonDetailState.Loading -> {
            LoadingIndicator()
        }
        is PokemonDetailState.Failure -> {
            val errorText = (state.value as PokemonDetailState.Failure).errorText
            Text("Error: $errorText")
        }
        is PokemonDetailState.Success -> {
            LaunchedEffect(key1 = true) {
                pokemonDetailViewModel.uiSingleTimeEvent.collect { event ->
                    when(event) {
                        is UiSingleTimeEvent.PopBackStack -> navController.popBackStack()
                        else -> Unit
                    }
                }
            }

            val pokemonDetail = (state.value as PokemonDetailState.Success).pokemonDetailModel
            val pokemonColor = UtilityFunctions.hexToColor(color)
            val textColor = UtilityFunctions.getTextColor(pokemonColor)

            Box(Modifier.fillMaxSize()) {
                PokemonDisplaySection(modifier = Modifier.fillMaxSize(), pokemonDetail, pokemonColor, textColor) {
                    pokemonDetailViewModel.onEvent(
                        PokemonDetailEvent.OnBackClicked
                    )
                }
                PokemonDetailSection(
                    Modifier.fillMaxWidth().fillMaxHeight(0.5f)
                    .align(Alignment.BottomCenter)
                    .shadow(20.dp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Color.White),
                    pokemonDetail
                )
            }
        }
        else -> Unit
    }
}