package com.example.cinemalab.data.repository

import com.example.cinemalab.data.remote.api.CollectionApi
import com.example.cinemalab.data.remote.dto.CollectionDto
import com.example.cinemalab.data.remote.dto.CollectionNameDto
import com.example.cinemalab.data.remote.dto.MovieDto
import com.example.cinemalab.data.remote.dto.MovieIdDto
import com.example.cinemalab.domain.repository.CollectionRepository
import retrofit2.Response
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val api: CollectionApi
) : CollectionRepository {

    override suspend fun get(token: String): List<CollectionDto> {
        return api.get(token)
    }

    override suspend fun post(token: String, collectionName: CollectionNameDto): CollectionDto {
        return api.post(token, collectionName)
    }

    override suspend fun delete(token: String, collectionId: String): Response<Void> {
        return api.delete(token, collectionId)
    }

    override suspend fun getCollectionMovies(token: String, collectionId: String): List<MovieDto> {
        return api.getCollectionMovies(token, collectionId)
    }

    override suspend fun addMovieToCollection(
        token: String,
        collectionId: String,
        movieId: MovieIdDto
    ) {
        return api.addMovieToCollection(token, collectionId, movieId)
    }

    override suspend fun deleteMovieFromCollection(
        token: String,
        collectionId: String,
        movieId: String
    ): Response<Void> {
        return api.deleteMovieFromCollection(token, collectionId, movieId)
    }

}