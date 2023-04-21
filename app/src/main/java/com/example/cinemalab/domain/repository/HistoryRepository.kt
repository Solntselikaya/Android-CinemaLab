package com.example.cinemalab.domain.repository

import com.example.cinemalab.data.remote.dto.HistoryDto

interface HistoryRepository {

    suspend fun getHistory(token: String): List<HistoryDto>

}