package com.example.cinemalab.data.repository

import com.example.cinemalab.data.remote.EpisodeApi
import com.example.cinemalab.data.remote.dto.EpisodeTimeDto
import com.example.cinemalab.domain.repository.EpisodeRepository
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val api: EpisodeApi
): EpisodeRepository {

    override suspend fun getEpisodePosition(token: String, episodeId: String): EpisodeTimeDto {
        return api.getEpisodePosition(token, episodeId)
    }

    override suspend fun postEpisodePosition(token: String, episodeId: String, body: EpisodeTimeDto) {
        api.postEpisodePosition(token, episodeId, body)
    }

}