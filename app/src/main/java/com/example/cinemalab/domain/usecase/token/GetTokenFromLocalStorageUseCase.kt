package com.example.cinemalab.domain.usecase.token

import android.content.Context
import com.example.cinemalab.data.remote.dto.AuthTokenPairDto
import com.example.cinemalab.data.repository.TokenRepository

class GetTokenFromLocalStorageUseCase(
    private val context: Context
) {

    private val tokenRepositoryImpl by lazy {
        TokenRepository(context)
    }

    fun execute(): AuthTokenPairDto {
        return tokenRepositoryImpl.getTokenFromLocalStorage()
    }
}