package com.example.cinemalab.domain.repository

import com.example.cinemalab.data.remote.dto.MovieDto

interface MovieRepository {

    suspend fun getMovies(token: String, filter: String): List<MovieDto>

}