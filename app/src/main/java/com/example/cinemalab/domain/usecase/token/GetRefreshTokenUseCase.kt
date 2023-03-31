package com.example.cinemalab.domain.usecase.token

import com.example.cinemalab.data.remote.dto.AuthTokenPairDto
import com.example.cinemalab.data.remote.dto.RefreshTokenDto
import com.example.cinemalab.domain.repository.AuthRepository
import javax.inject.Inject

class GetRefreshTokenUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(refreshToken: RefreshTokenDto): AuthTokenPairDto =
        repository.getRefreshToken(refreshToken)
}