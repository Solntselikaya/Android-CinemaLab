package com.example.cinemalab.data.remote

import com.example.cinemalab.data.remote.dto.ProfileInfoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ProfileApi {

    @GET("profile")
    suspend fun getProfileInfo(@Header("Authorization") token: String): ProfileInfoDto

    @POST("profile/avatar")
    suspend fun uploadPhoto(@Body body: String)

}