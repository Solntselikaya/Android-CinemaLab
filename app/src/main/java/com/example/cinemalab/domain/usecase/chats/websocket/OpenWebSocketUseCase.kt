package com.example.cinemalab.domain.usecase.chats.websocket

import android.content.Context
import com.example.cinemalab.domain.repository.ChatsRepository
import com.example.cinemalab.domain.usecase.token.GetTokenFromLocalStorageUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class OpenWebSocketUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: ChatsRepository
) {
    operator fun invoke(chatId: String) {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        repository.initializeWebSocket(chatId, token.accessToken)
    }
}