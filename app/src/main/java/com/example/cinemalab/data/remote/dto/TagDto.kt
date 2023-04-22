package com.example.cinemalab.data.remote.dto

import com.example.cinemalab.domain.model.TagModel

data class TagDto(
    val categoryName: String,
    val tagId: String,
    val tagName: String
)

fun TagDto.toTagModel(): TagModel {
    return TagModel(tagName)
}