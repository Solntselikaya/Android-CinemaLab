package com.example.cinemalab.domain.repository

import com.example.cinemalab.data.remote.dto.MessageDto
import kotlinx.coroutines.flow.Flow

interface ChatsRepository {
    fun initializeWebSocket(chatId: String, token: String)

    fun getMessages(): Flow<MessageDto?>

    fun sendMessage(message: String)

    fun closeWebSocket()
}