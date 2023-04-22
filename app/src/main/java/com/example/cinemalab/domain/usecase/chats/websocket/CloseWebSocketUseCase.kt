package com.example.cinemalab.domain.usecase.chats.websocket

import com.example.cinemalab.domain.repository.ChatsRepository
import javax.inject.Inject

class CloseWebSocketUseCase @Inject constructor(
    private val repository: ChatsRepository
) {
    operator fun invoke() {
        repository.closeWebSocket()
    }
}