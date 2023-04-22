package com.example.cinemalab.domain.usecase.movie

import android.content.Context
import com.example.cinemalab.domain.repository.MovieRepository
import com.example.cinemalab.domain.usecase.token.GetTokenFromLocalStorageUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DislikeMovieInCompilationUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: MovieRepository
) {

    suspend operator fun invoke(movieId: String) {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        val bearerToken = "Bearer ${token.accessToken}"

        return repository.dislike(bearerToken, movieId)
    }

}