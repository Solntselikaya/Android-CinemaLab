package com.example.cinemalab.data.remote.api

import com.example.cinemalab.data.remote.dto.EpisodeTimeDto
import retrofit2.http.*

interface EpisodeApi {

    @GET("episodes/{episodeId}/time")
    suspend fun getEpisodePosition(
        @Header("Authorization") token: String,
        @Path("episodeId") id: String
    ): EpisodeTimeDto

    @POST("episodes/{episodeId}/time")
    suspend fun postEpisodePosition(
        @Header("Authorization") token: String,
        @Path("episodeId") id: String,
        @Body time: EpisodeTimeDto
    )

}