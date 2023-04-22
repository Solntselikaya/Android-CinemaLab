package com.example.cinemalab.data.repository

import com.example.cinemalab.data.remote.api.MovieApi
import com.example.cinemalab.data.remote.dto.EpisodeDto
import com.example.cinemalab.data.remote.dto.MovieDto
import com.example.cinemalab.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {

    override suspend fun getMovies(token: String, filter: String): List<MovieDto> {
        return api.getMovies(token, filter)
    }

    override suspend fun getEpisodes(token: String, movieId: String): List<EpisodeDto> {
        return api.getEpisodes(token, movieId)
    }

    override suspend fun dislike(token: String, movieId: String) {
        return api.dislike(token, movieId)
    }

}