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
    val creationDate = creationDateTime.substringBefore("T")

    val creationTimeElements = creationDateTime.substringAfter("T").split(':')
    val creationTime = creationTimeElements[0] + ":" + creationTimeElements[1]

    return MessageModel(
        authorAvatar = authorAvatar,
        authorId = authorId,
        authorName = authorName,
        creationDate = creationDate,
        creationTime = creationTime,
        text = text
    )
}