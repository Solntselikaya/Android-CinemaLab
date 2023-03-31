package com.example.cinemalab.data.remote.dto

import com.example.cinemalab.domain.model.PromotedCoverModel

data class PromotedCoverDto(
    val backgroundImage: String,
    val foregroundImage: String
)

fun PromotedCoverDto.toPromotedCoverModel(): PromotedCoverModel {
    return PromotedCoverModel(cover = backgroundImage)
}