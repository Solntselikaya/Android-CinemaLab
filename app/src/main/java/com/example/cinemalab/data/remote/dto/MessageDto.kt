package com.example.cinemalab.data.remote.dto

import com.example.cinemalab.domain.model.MessageModel

data class MessageDto(
    val authorAvatar: String,
    val authorId: String,
    val authorName: String,
    val creationDateTime: String,
    val messageId: String,
    val text: String
)

fun MessageDto.toMessageModel(): MessageModel {
    return MessageModel(
        authorAvatar = authorAvatar,
        authorId = authorId,
        authorName = authorName,
        creationDateTime = creationDateTime,
        text = text
    )
}