package com.example.cinemalab.data.storage

import com.example.cinemalab.data.remote.dto.AuthTokenPairDto

interface TokenStorage {
    companion object {
        const val TOKEN_KEY = "userToken"
        const val REFRESH_TOKEN_KEY = "refreshUserToken"
        const val TOKEN_EXPIRE_TIME = "tokenExpireTime"
        const val REFRESH_TOKEN_EXPIRE_TIME = "refreshTokenExpireTime"
    }

    fun saveToken(token: AuthTokenPairDto)

    fun getToken(): AuthTokenPairDto

    fun deleteToken()
}