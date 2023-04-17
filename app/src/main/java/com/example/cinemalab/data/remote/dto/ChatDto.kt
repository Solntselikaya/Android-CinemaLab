package com.example.cinemalab.data.remote.dto

import com.example.cinemalab.domain.model.ChatModel

data class ChatDto(
    val chatId: String,
    val chatName: String,
    val lastMessage: MessageDto? = null
)

fun ChatDto.toChatModel(): ChatModel {
    return ChatModel(
        chatId = chatId,
        chatName = chatName,
        lastMessage = lastMessage?.toMessageModel()
    )
}