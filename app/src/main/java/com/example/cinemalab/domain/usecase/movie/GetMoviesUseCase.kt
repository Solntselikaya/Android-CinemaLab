package com.example.cinemalab.domain.usecase.movie

import android.content.Context
import com.example.cinemalab.data.remote.dto.MovieDto
import com.example.cinemalab.domain.repository.MovieRepository
import com.example.cinemalab.domain.usecase.token.GetTokenFromLocalStorageUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: MovieRepository
) {

    suspend operator fun invoke(filter: String): List<MovieDto> {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        val bearerToken = "Bearer ${token.accessToken}"

        return repository.getMovies(bearerToken, filter)
    }

}