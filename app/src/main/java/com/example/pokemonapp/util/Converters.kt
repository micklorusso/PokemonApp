package com.example.pokemonapp.util

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.joinToString(",") // Convert List<String> to a comma-separated String
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.split(",") ?: listOf() // Convert String back to List<String>
    }
}