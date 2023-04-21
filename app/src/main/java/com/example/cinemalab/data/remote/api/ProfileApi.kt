package com.example.cinemalab.data.remote.api

import com.example.cinemalab.data.remote.dto.ProfileInfoDto
import okhttp3.MultipartBody
import retrofit2.http.*

interface ProfileApi {

    @GET("profile")
    suspend fun getProfileInfo(
        @Header("Authorization") token: String
    ): ProfileInfoDto

    @Multipart
    @POST("profile/avatar")
    suspend fun uploadPhoto(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    )

}