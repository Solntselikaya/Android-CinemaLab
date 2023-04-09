package com.example.cinemalab.domain.model

data class EpisodeModel(
    val description: String,
    val episodeId: String,
    val filePath: String,
    val name: String,
    val preview: String,
    val year: String
)