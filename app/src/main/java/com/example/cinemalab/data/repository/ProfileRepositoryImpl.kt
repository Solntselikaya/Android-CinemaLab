package com.example.cinemalab.data.repository

import com.example.cinemalab.data.remote.ProfileApi
import com.example.cinemalab.data.remote.dto.AuthTokenPairDto
import com.example.cinemalab.data.remote.dto.ProfileInfoDto
import com.example.cinemalab.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApi
) : ProfileRepository {

    override suspend fun getProfile(token: String): ProfileInfoDto {
        return api.getProfileInfo(token)
    }

    override suspend fun uploadPhoto(body: String) {
        return api.uploadPhoto(body)
    }

}