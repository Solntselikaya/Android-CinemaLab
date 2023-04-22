package com.example.cinemalab.data.remote.dto

data class AuthTokenPairDto(
    val accessToken: String,
    val accessTokenExpiresIn: Int,
    val refreshToken: String,
    val refreshTokenExpiresIn: Int
)