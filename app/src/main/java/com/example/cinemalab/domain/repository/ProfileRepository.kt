package com.example.cinemalab.domain.repository

import com.example.cinemalab.data.remote.dto.ProfileInfoDto
import okhttp3.MultipartBody

interface ProfileRepository {

    suspend fun getProfile(token: String): ProfileInfoDto

    suspend fun uploadPhoto(token: String, image: MultipartBody.Part)
}