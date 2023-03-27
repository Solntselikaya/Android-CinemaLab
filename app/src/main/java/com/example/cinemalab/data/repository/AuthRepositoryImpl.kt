package com.example.cinemalab.data.repository

import com.example.cinemalab.data.remote.AuthApi
import com.example.cinemalab.data.remote.dto.AuthTokenPairDto
import com.example.cinemalab.data.remote.dto.LoginDto
import com.example.cinemalab.data.remote.dto.RefreshTokenDto
import com.example.cinemalab.data.remote.dto.RegistrationBodyDto
import com.example.cinemalab.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
) : AuthRepository {

    override suspend fun register(body: RegistrationBodyDto): AuthTokenPairDto {
        return api.register(body)
    }

    override suspend fun login(body: LoginDto): AuthTokenPairDto {
        return api.login(body)
    }

    override suspend fun getRefreshToken(body: RefreshTokenDto): AuthTokenPairDto {
        return api.getRefreshToken(body)
    }
}