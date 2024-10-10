package com.example.pokemonapp.di

import com.example.pokemonapp.features.auth.data.service.AccountServiceImpl
import com.example.pokemonapp.features.auth.data.service.FavouriteServiceImpl
import com.example.pokemonapp.features.auth.data.service.FirebaseServiceImpl
import com.example.pokemonapp.features.auth.data.service.StorageServiceImpl
import com.example.pokemonapp.features.auth.domain.AccountService
import com.example.pokemonapp.features.auth.domain.FavouriteService
import com.example.pokemonapp.features.auth.domain.FirebaseService
import com.example.pokemonapp.features.auth.domain.StorageService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
    @Binds
    abstract fun provideFirebaseService(impl: FirebaseServiceImpl): FirebaseService
    @Binds
    abstract fun provideFavouriteService(impl: FavouriteServiceImpl): FavouriteService
}