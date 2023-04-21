package com.example.cinemalab.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodeModel(
    val description: String,
    val director: String,
    val episodeId: String,
    val filePath: String,
    val name: String,
    val preview: String,
    val year: String,
    val runtime: Int
) : Parcelable