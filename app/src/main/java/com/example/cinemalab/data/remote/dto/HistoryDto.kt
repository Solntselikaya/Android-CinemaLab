package com.example.cinemalab.data.remote.dto

import com.example.cinemalab.domain.model.HistoryModel

data class HistoryDto(
    val episodeId: String,
    val episodeName: String,
    val filePath: String,
    val movieId: String,
    val movieName: String,
    val preview: String,
    val time: Int
)

fun HistoryDto.toHistoryModel(): HistoryModel {
    return HistoryModel(
        episodeId = episodeId,
        movieId = movieId
    )
}