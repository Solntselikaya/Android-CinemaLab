package com.example.cinemalab.domain.usecase.auth

import com.example.cinemalab.data.remote.dto.AuthTokenPairDto
import com.example.cinemalab.data.remote.dto.RegistrationBodyDto
import com.example.cinemalab.domain.repository.AuthRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(registrationBody: RegistrationBodyDto): AuthTokenPairDto =
        repository.register(registrationBody)
}