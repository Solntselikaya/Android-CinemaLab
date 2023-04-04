package com.example.cinemalab.data.repository

import com.example.cinemalab.data.remote.CollectionApi
import com.example.cinemalab.data.remote.dto.CollectionDto
import com.example.cinemalab.data.remote.dto.CollectionNameDto
import com.example.cinemalab.data.remote.dto.MovieDto
import com.example.cinemalab.domain.repository.CollectionRepository
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val api: CollectionApi
) : CollectionRepository {

    override suspend fun get(token: String): List<CollectionDto> {
        return api.get(token)
    }

    override suspend fun post(token: String, collectionName: CollectionNameDto) {
        return api.post(token, collectionName)
    }

    override suspend fun delete(token: String, collectionId: String) {
        api.delete(token, collectionId)
    }

    override suspend fun getCollectionMovies(token: String, collectionId: String): List<MovieDto> {
        return api.getCollectionMovies(token, collectionId)
    }

    override suspend fun addMovieToCollection(
        token: String,
        collectionId: String,
        movieId: String
    ) {
        return api.addMovieToCollection(token, collectionId, movieId)
    }

    override suspend fun deleteMovieFromCollection(
        token: String,
        collectionId: String,
        movieId: String
    ) {
        return api.deleteMovieFromCollection(token, collectionId, movieId)
    }

}