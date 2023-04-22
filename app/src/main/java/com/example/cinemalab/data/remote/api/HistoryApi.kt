package com.example.cinemalab.data.remote.api

import com.example.cinemalab.data.remote.dto.HistoryDto
import retrofit2.http.*

interface HistoryApi {

    @GET("history")
    suspend fun getHistory(
        @Header("Authorization") token: String
    ): List<HistoryDto>

}