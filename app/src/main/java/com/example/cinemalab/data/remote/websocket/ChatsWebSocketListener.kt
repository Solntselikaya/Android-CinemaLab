package com.example.cinemalab.data.remote.websocket

import android.util.Log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.runBlocking
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class ChatsWebSocketListener : WebSocketListener() {
    private val messages = MutableSharedFlow<String>()

    fun getMessages(): SharedFlow<String> = messages

    override fun onOpen(webSocket: WebSocket, response: Response) {
        runBlocking {
            messages.emit(response.message)
        }
        Log.d("SOCKET", "OPEN")
        super.onOpen(webSocket, response)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        runBlocking {
            messages.emit(text)
        }
        super.onMessage(webSocket, text)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("SOCKET", "CLOSE")
        super.onClosing(webSocket, code, reason)
    }
}