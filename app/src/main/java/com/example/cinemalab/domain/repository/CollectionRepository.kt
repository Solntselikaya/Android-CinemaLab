package com.example.cinemalab.domain.repository

import com.example.cinemalab.data.remote.dto.CollectionDto
import com.example.cinemalab.data.remote.dto.CollectionNameDto
import com.example.cinemalab.data.remote.dto.MovieDto
import com.example.cinemalab.data.remote.dto.MovieIdDto
import retrofit2.Response

interface CollectionRepository {

    suspend fun get(token: String): List<CollectionDto>

    suspend fun post(token: String, collectionName: CollectionNameDto): CollectionDto

    suspend fun delete(token: String, collectionId: String): Response<Void>

    suspend fun getCollectionMovies(
        token: String,
        collectionId: String
    ): List<MovieDto>

    suspend fun addMovieToCollection(
        token: String,
        collectionId: String,
        movieId: MovieIdDto
    )

    suspend fun deleteMovieFromCollection(
        token: String,
        collectionId: String,
        movieId: String
    ): Response<Void>

}