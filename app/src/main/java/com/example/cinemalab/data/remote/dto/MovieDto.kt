package com.example.cinemalab.data.remote.dto

import com.example.cinemalab.domain.model.AgeLimitEnum
import com.example.cinemalab.domain.model.MovieModel
import com.example.cinemalab.domain.model.MovieShortModel

data class MovieDto(
    val age: String,
    val chatInfo: ChatDto? = null,
    val description: String,
    val imageUrls: List<String>,
    val movieId: String,
    val name: String,
    val poster: String,
    val tags: List<TagDto>
)

fun MovieDto.toMovieModel(): MovieModel {

    val ageAsEnum = when (age) {
        "18+" -> AgeLimitEnum.Eighteen
        "16+" -> AgeLimitEnum.Sixteen
        "12+" -> AgeLimitEnum.Twelve
        "6+" -> AgeLimitEnum.Six
        else -> AgeLimitEnum.Zero
    }

    return MovieModel(
        age = ageAsEnum,
        chatInfo = chatInfo?.toChatModel(),
        description = description,
        imageUrls = imageUrls,
        movieId = movieId,
        name = name,
        poster = poster,
        tags = tags.map { it.toTagModel() }
    )
}

fun MovieDto.toMovieShortModel(): MovieShortModel {
    return MovieShortModel(
        id = movieId,
        poster = poster,
        name = name,
        chatInfo = chatInfo?.toChatModel()
    )
}