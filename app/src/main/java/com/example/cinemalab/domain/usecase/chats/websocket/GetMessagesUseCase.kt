package com.example.cinemalab.domain.usecase.chats.websocket

import com.example.cinemalab.data.remote.dto.MessageDto
import com.example.cinemalab.domain.repository.ChatsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val repository: ChatsRepository
) {
    operator fun invoke(): Flow<MessageDto?> {
        return repository.getMessages()
    }
}