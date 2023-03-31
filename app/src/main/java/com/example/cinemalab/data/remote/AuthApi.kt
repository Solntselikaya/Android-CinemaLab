package com.example.cinemalab.data.remote

import com.example.cinemalab.data.remote.dto.AuthTokenPairDto
import com.example.cinemalab.data.remote.dto.LoginDto
import com.example.cinemalab.data.remote.dto.RefreshTokenDto
import com.example.cinemalab.data.remote.dto.RegistrationBodyDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/register")
    suspend fun register(@Body body: RegistrationBodyDto): AuthTokenPairDto

    @POST("auth/login")
    suspend fun login(@Body body: LoginDto): AuthTokenPairDto

    @POST("auth/refresh")
    suspend fun getRefreshToken(@Body body: RefreshTokenDto): AuthTokenPairDto

}