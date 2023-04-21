package com.example.cinemalab.data.repository

import android.util.Log
import com.example.cinemalab.data.remote.dto.MessageDto
import com.example.cinemalab.data.remote.websocket.ChatsWebSocket
import com.example.cinemalab.domain.repository.ChatsRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChatsRepositoryImpl @Inject constructor(
    private val chatsWebSocket: ChatsWebSocket
) : ChatsRepository {

    override fun initializeWebSocket(chatId: String, token: String) {
        chatsWebSocket.initializeWebSocket(chatId, token)
    }

    override fun getMessages(): Flow<MessageDto?> {
        return flow {
            chatsWebSocket.getMessages().collect {
                Log.d("SOCKET", it)
                if (it != "Switching Protocols") {
                    emit(Gson().fromJson(it, MessageDto::class.java))
                }
            }
        }
    }

    override fun sendMessage(message: String) {
        chatsWebSocket.sendMessage(message)
    }

    override fun closeWebSocket() {
        chatsWebSocket.close()
    }
}