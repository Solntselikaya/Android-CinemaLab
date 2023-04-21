package com.example.cinemalab.domain.usecase.chats.websocket

import com.example.cinemalab.domain.repository.ChatsRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: ChatsRepository
) {
    operator fun invoke(message: String) {
        repository.sendMessage(message)
    }
}