package com.example.cinemalab.domain.model

data class ChatElementModel(
    val type: ChatElementType,
    val text: String,
    val nameAndTime: String? = null,
    val authorAvatar: String? = null,
    val authorId: String? = null
)