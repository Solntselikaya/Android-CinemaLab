package com.example.cinemalab.data.remote

import com.example.cinemalab.data.remote.dto.PromotedCoverDto
import retrofit2.http.GET
import retrofit2.http.Header

interface CoverApi {

    @GET("cover")
    suspend fun getPromotedBanner(@Header("Authorization") token: String): PromotedCoverDto

}