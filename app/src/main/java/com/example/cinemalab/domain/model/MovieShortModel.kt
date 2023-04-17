package com.example.cinemalab.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieShortModel(
    val id: String,
    val poster: String,
    val name: String,
    val chatInfo: ChatModel? = null
): Parcelable
