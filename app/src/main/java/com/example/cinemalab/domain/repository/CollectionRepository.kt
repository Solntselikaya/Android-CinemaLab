package com.example.cinemalab.domain.repository

import com.example.cinemalab.data.remote.dto.CollectionDto
import com.example.cinemalab.data.remote.dto.MovieDto

interface CollectionRepository {

    suspend fun get(token: String): List<CollectionDto>

    suspend fun post(token: String, collectionName: String)

    suspend fun delete(token: String, collectionId: String)

    suspend fun getCollectionMovies(
        token: String,
        collectionId: String
    ): List<MovieDto>

    suspend fun addMovieToCollection(
        token: String,
        collectionId: String,
        movieId: String
    )

    suspend fun deleteMovieFromCollection(
        token: String,
        collectionId: String,
        movieId: String
    )

}