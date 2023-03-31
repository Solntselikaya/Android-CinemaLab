package com.example.cinemalab.domain.usecase.profile

import android.content.Context
import com.example.cinemalab.data.remote.dto.ProfileInfoDto
import com.example.cinemalab.domain.repository.ProfileRepository
import com.example.cinemalab.domain.usecase.token.GetTokenFromLocalStorageUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetProfileInfoUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(): ProfileInfoDto {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        val bearerToken = "Bearer ${token.accessToken}"

        return repository.getProfile(bearerToken)
    }
}