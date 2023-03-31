package com.example.cinemalab.domain.model

data class MovieModel(
    val age: String,
    val chatInfo: ChatInfoModel,
    val description: String,
    val imageUrls: List<String>,
    val movieId: String,
    val name: String,
    val poster: String,
    val tags: List<TagModel>
)