package com.example.cinemalab.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShortChatInfoModel(
    val id: String,
    val name: String
) : Parcelable
