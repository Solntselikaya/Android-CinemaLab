package com.example.cinemalab.data.remote

import com.example.cinemalab.data.remote.dto.MovieDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieApi {

    @GET("movies")
    suspend fun getMovies(
        @Header("Authorization") token: String,
        @Query("filter") filter: String
    ): List<MovieDto>

}