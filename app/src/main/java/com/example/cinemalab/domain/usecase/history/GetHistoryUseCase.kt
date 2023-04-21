package com.example.cinemalab.domain.usecase.history

import android.content.Context
import com.example.cinemalab.data.remote.dto.HistoryDto
import com.example.cinemalab.domain.repository.HistoryRepository
import com.example.cinemalab.domain.usecase.token.GetTokenFromLocalStorageUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: HistoryRepository
) {

    suspend operator fun invoke(): List<HistoryDto> {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        val bearerToken = "Bearer ${token.accessToken}"

        return repository.getHistory(bearerToken)
    }

}