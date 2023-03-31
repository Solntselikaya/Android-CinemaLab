package com.example.cinemalab.domain.usecase.token

import android.content.Context
import com.example.cinemalab.data.remote.dto.AuthTokenPairDto
import com.example.cinemalab.data.repository.TokenRepository

class SaveTokenUseCase(private val context: Context) {

    private val tokenRepository by lazy {
        TokenRepository(context)
    }

    fun execute(token: AuthTokenPairDto) {
        tokenRepository.saveTokenToLocalStorage(token)
    }
}