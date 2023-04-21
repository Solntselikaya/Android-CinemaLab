package com.example.cinemalab.domain.usecase.episode

import android.content.Context
import com.example.cinemalab.data.remote.dto.EpisodeTimeDto
import com.example.cinemalab.domain.repository.EpisodeRepository
import com.example.cinemalab.domain.usecase.token.GetTokenFromLocalStorageUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetEpisodePositionUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: EpisodeRepository
) {

    suspend operator fun invoke(episodeId: String): EpisodeTimeDto {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        val bearerToken = "Bearer ${token.accessToken}"

        return repository.getEpisodePosition(bearerToken, episodeId)
    }

}