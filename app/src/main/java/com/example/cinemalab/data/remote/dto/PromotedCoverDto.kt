package com.example.cinemalab.data.remote.dto

import com.example.cinemalab.domain.model.PromotedCoverModel

data class PromotedCoverDto(
    val backgroundImage: String,
    val foregroundImage: String
)

fun PromotedCoverDto.toPromotedCoverModel(): PromotedCoverModel {

    val regex = Regex("\t")
    val coverUrl = regex.replace(backgroundImage, "")
    return PromotedCoverModel(cover = coverUrl)
}