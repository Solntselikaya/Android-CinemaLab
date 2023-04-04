package com.example.cinemalab.data.remote.dto

import com.example.cinemalab.domain.model.CollectionModel

data class CollectionDto(
    val collectionId: String,
    val name: String
)

fun CollectionDto.toCollectionModel(): CollectionModel {
    return CollectionModel(
        id = collectionId,
        name = name
    )
}