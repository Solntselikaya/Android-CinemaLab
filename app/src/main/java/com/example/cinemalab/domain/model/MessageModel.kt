package com.example.cinemalab.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageModel(
    val authorAvatar: String,
    val authorId: String,
    val authorName: String,
    val creationDateTime: String,
    val text: String
): Parcelable