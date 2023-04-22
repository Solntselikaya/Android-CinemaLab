package com.example.cinemalab.domain.repository

import com.example.cinemalab.data.remote.dto.AuthTokenPairDto
import com.example.cinemalab.data.remote.dto.LoginDto
import com.example.cinemalab.data.remote.dto.RefreshTokenDto
import com.example.cinemalab.data.remote.dto.RegistrationBodyDto

interface AuthRepository {

    suspend fun register(body: RegistrationBodyDto): AuthTokenPairDto

    suspend fun login(body: LoginDto): AuthTokenPairDto

    suspend fun getRefreshToken(body: RefreshTokenDto): AuthTokenPairDto

}