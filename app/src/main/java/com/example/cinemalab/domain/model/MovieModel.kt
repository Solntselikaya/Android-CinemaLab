package com.example.cinemalab.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieModel(
    val age: AgeLimitEnum,
    val chatInfo: ChatModel? = null,
    val description: String,
    val imageUrls: List<String>,
    val movieId: String,
    val name: String,
    val poster: String,
    val tags: List<TagModel>
): Parcelable

fun MovieModel.toMovieShortModel(): MovieShortModel {
    return MovieShortModel(
        id = movieId,
        poster = poster,
        name = name,
        chatInfo = chatInfo
    )
}