package com.example.cinemalab.domain.repository

import com.example.cinemalab.data.remote.dto.EpisodeDto
import com.example.cinemalab.data.remote.dto.MovieDto

interface MovieRepository {

    suspend fun getMovies(token: String, filter: String): List<MovieDto>

    suspend fun getEpisodes(token: String, movieId: String): List<EpisodeDto>

    suspend fun dislike(token: String, movieId: String)

}