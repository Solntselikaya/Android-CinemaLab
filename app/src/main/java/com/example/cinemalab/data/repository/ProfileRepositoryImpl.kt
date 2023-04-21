package com.example.cinemalab.data.repository

import com.example.cinemalab.data.remote.api.ProfileApi
import com.example.cinemalab.data.remote.dto.ProfileInfoDto
import com.example.cinemalab.domain.repository.ProfileRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApi
) : ProfileRepository {

    override suspend fun getProfile(token: String): ProfileInfoDto {
        return api.getProfileInfo(token)
    }

    override suspend fun uploadPhoto(token: String, image: MultipartBody.Part) {
        api.uploadPhoto(token, image)
    }

}