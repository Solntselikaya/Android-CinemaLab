package com.example.cinemalab.data.remote.dto

import com.example.cinemalab.domain.model.MovieModel

data class MovieDto(
    val age: String,
    val chatInfo: ChatInfoDto,
    val description: String,
    val imageUrls: List<String>,
    val movieId: String,
    val name: String,
    val poster: String,
    val tags: List<TagDto>
)

fun MovieDto.toMovieModel(): MovieModel {
    return MovieModel(
        age = age,
        chatInfo = chatInfo.toChatInfoModel(),
        description = description,
        imageUrls = imageUrls,
        movieId = movieId,
        name = name,
        poster = poster,
        tags = tags.map { it.toTagModel() }
    )
}