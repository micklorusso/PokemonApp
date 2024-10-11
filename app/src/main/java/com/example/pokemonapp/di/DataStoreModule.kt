package com.example.pokemonapp.di

import android.app.Application
import android.content.Context
import com.example.pokemonapp.features.auth.presentation.settings.viewModel.SettingsDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideSettingsDataStore(context: Context): SettingsDataStore {
        return SettingsDataStore(context)
    }
}