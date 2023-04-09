package com.example.cinemalab.data.remote.dto

import com.example.cinemalab.domain.model.EpisodeModel

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

fun EpisodeDto.toEpisodeModel(): EpisodeModel {
    return EpisodeModel(
        description = description,
        episodeId = episodeId,
        filePath = filePath,
        name = name,
        preview = preview,
        year = year
    )
}