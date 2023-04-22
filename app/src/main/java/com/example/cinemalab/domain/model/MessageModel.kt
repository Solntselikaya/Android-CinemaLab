package com.example.cinemalab.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageModel(
    val authorAvatar: String? = null,
    val authorId: String,
    val authorName: String,
    val creationDate: String,
    val creationTime: String,
    val text: String
) : Parcelable