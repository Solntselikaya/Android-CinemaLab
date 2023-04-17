package com.example.cinemalab.data.remote

import com.example.cinemalab.data.remote.dto.EpisodeTimeDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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