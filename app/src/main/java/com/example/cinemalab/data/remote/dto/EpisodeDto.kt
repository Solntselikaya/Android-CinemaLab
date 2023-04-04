package com.example.cinemalab.data.remote.dto

data class EpisodeDto(
    val description: String,
    val director: String,
    val episodeId: String,
    val filePath: String,
    val images: List<String>,
    val name: String,
    val preview: String,
    val runtime: Int,
    val stars: List<String>,
    val year: String
)