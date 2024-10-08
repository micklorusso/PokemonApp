package com.example.pokemonapp.di

import android.content.Context
import androidx.room.Room
import com.example.pokemonapp.features.pokedex.data.datasources.localDatabase.PokemonDatabase
import com.example.pokemonapp.util.Constants.POKEMON_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PokemonDatabase {
        return Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            POKEMON_DATABASE
        ).build()
    }

}