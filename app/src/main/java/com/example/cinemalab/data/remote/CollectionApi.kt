package com.example.cinemalab.data.remote

import com.example.cinemalab.data.remote.dto.CollectionDto
import com.example.cinemalab.data.remote.dto.CollectionNameDto
import com.example.cinemalab.data.remote.dto.MovieDto
import com.example.cinemalab.data.remote.dto.MovieIdDto
import retrofit2.Response
import retrofit2.http.*

interface CollectionApi {

    @GET("collections")
    suspend fun get(
        @Header("Authorization") token: String
    ): List<CollectionDto>

    @POST("collections")
    suspend fun post(
        @Header("Authorization") token: String,
        @Body collectionName: CollectionNameDto
    ): CollectionDto

    @DELETE("collections/{collectionId}")
    suspend fun delete(
        @Header("Authorization") token: String,
        @Path("collectionId") collectionId: String
    ): Response<Void>

    @GET("collections/{collectionId}/movies")
    suspend fun getCollectionMovies(
        @Header("Authorization") token: String,
        @Path("collectionId") id: String
    ): List<MovieDto>

    @POST("collections/{collectionId}/movies")
    suspend fun addMovieToCollection(
        @Header("Authorization") token: String,
        @Path("collectionId") collectionId: String,
        @Body movieId: MovieIdDto
    )

    // тут бади или квери??????
    @DELETE("collections/{collectionId}/movies")
    suspend fun deleteMovieFromCollection(
        @Header("Authorization") token: String,
        @Path("collectionId") collectionId: String,
        @Body movieId: String
    )

}