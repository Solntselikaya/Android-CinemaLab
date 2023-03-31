package com.example.cinemalab.domain.usecase

import com.example.cinemalab.data.remote.dto.AuthTokenPairDto
import com.example.cinemalab.data.remote.dto.LoginDto
import com.example.cinemalab.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(credentials: LoginDto): AuthTokenPairDto =
        repository.login(credentials)
}