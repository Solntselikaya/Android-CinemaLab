package com.example.cinemalab.data.repository

import com.example.cinemalab.data.remote.api.HistoryApi
import com.example.cinemalab.data.remote.dto.HistoryDto
import com.example.cinemalab.domain.repository.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val api: HistoryApi
) : HistoryRepository {

    override suspend fun getHistory(token: String): List<HistoryDto> {
        return api.getHistory(token)
    }

}