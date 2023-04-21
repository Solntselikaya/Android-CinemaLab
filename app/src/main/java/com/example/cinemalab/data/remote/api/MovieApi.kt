package com.example.cinemalab.data.remote.api

import com.example.cinemalab.data.remote.dto.EpisodeDto
import com.example.cinemalab.data.remote.dto.MovieDto
import retrofit2.http.*

interface MovieApi {

    @GET("movies")
    suspend fun getMovies(
        @Header("Authorization") token: String,
        @Query("filter") filter: String
    ): List<MovieDto>

    @GET("movies/{movieId}/episodes")
    suspend fun getEpisodes(
        @Header("Authorization") token: String,
        @Path("movieId") movieId: String
    ): List<EpisodeDto>

    @POST("movies/{movieId}/dislike")
    suspend fun dislike(
        @Header("Authorization") token: String,
        @Path("movieId") movieId: String
    )

}