package com.example.cinemalab.data.remote.dto

import com.example.cinemalab.domain.model.ChatInfoModel

data class ChatInfoDto(
    val chatId: String,
    val chatName: String
)

fun ChatInfoDto.toChatInfoModel(): ChatInfoModel {
    return ChatInfoModel(
        chatId = chatId,
        chatName = chatName
    )
}