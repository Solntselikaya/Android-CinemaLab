package com.example.cinemalab.data.remote.dto

import com.example.cinemalab.domain.model.ChatInfoModel

data class ChatInfoDto(
    val chatName: String
)

fun ChatInfoDto.toChatInfoModel(): ChatInfoModel {
    return ChatInfoModel(chatName)
}