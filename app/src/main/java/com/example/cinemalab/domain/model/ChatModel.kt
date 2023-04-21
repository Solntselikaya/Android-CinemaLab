package com.example.cinemalab.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatModel(
    val chatId: String,
    val chatName: String,
    val lastMessage: MessageModel? = null
) : Parcelable

fun ChatModel.toShortChatInfoModel(): ShortChatInfoModel {
    return ShortChatInfoModel(
        id = chatId,
        name = chatName
    )
}