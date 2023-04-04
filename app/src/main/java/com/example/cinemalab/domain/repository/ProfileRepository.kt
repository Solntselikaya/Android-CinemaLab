package com.example.cinemalab.domain.repository

import com.example.cinemalab.data.remote.dto.ProfileInfoDto

interface ProfileRepository {

    suspend fun getProfile(token: String): ProfileInfoDto

    suspend fun uploadPhoto(body: String)
}