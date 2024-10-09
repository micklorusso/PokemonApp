package com.example.pokemonapp.features.pokedex.data.datasources.api

import com.example.pokemonapp.features.pokedex.data.model.pokemonDetail.PokemonDetailModel
import com.example.pokemonapp.features.pokedex.data.model.pokemonList.PokemonListModel
import com.example.pokemonapp.features.pokedex.data.model.pokemonSpecies.PokemonSpeciesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonList(@Query("offset") page: Int, @Query("limit") perPage: Int): Response<PokemonListModel>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(@Path("id") id: Int): Response<PokemonDetailModel>

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: Int): Response<PokemonSpeciesModel>
}