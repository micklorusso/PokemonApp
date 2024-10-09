package com.example.pokemonapp.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

class UtilityFunctions {
    companion object{
        fun hexToColor(hex: String): Color{
            return Color(android.graphics.Color.parseColor(hex))
        }

        fun getTextColor(color: Color): Color{
            val textColor = if (color.luminance() > 0.5f) Color.Black else Color.White
            return textColor
        }
    }
}