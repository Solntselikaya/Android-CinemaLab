package com.example.cinemalab.domain.repository

import com.example.cinemalab.data.remote.dto.EpisodeTimeDto

interface EpisodeRepository {

    suspend fun getEpisodePosition(
        token: String,
        episodeId: String
    ): EpisodeTimeDto

    suspend fun postEpisodePosition(
        token: String,
        episodeId: String,
        body: EpisodeTimeDto
    )

}