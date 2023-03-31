package com.example.cinemalab.data.repository

import com.example.cinemalab.data.remote.MovieApi
import com.example.cinemalab.data.remote.dto.MovieDto
import com.example.cinemalab.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
): MovieRepository {

    override suspend fun getMovies(token: String, filter: String): List<MovieDto> {
        return api.getMovies(token, filter)
    }

}