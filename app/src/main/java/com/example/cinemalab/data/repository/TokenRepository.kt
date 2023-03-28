package com.example.cinemalab.data.repository

import android.content.Context
import com.example.cinemalab.data.remote.dto.AuthTokenPairDto
import com.example.cinemalab.data.storage.SharedPreferencesStorage

class TokenRepository(
    context: Context
) {

    private val sharedPreferencesStorage by lazy {
        SharedPreferencesStorage(context)
    }

    fun saveTokenToLocalStorage(token: AuthTokenPairDto) {
        sharedPreferencesStorage.saveToken(token)
    }

    fun getTokenFromLocalStorage(): AuthTokenPairDto {
        return sharedPreferencesStorage.getToken()
    }

    fun deleteTokenFromLocalStorage() {
        sharedPreferencesStorage.deleteToken()
    }
}