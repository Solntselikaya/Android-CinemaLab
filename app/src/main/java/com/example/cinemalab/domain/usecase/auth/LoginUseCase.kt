package com.example.cinemalab.domain.usecase.auth

import com.example.cinemalab.data.remote.dto.AuthTokenPairDto
import com.example.cinemalab.data.remote.dto.LoginDto
import com.example.cinemalab.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    //нужно запросы отправлять в Dispatchers.IO и например вот так в юзкейсе можно это сделать
    //чтобы в лаунч не прописывать
    suspend operator fun invoke(credentials: LoginDto): AuthTokenPairDto =
        withContext(Dispatchers.IO) {
            repository.login(credentials)
        }
}