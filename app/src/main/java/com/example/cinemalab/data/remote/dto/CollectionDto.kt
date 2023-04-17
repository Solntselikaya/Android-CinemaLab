package com.example.cinemalab.data.remote.dto

import com.example.cinemalab.R
import com.example.cinemalab.data.db.entities.CollectionEntity
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

fun CollectionDto.toCollectionEntity(icon: Int? = R.drawable.collection_icon_01): CollectionEntity {
    return CollectionEntity(
        id = collectionId,
        name = name,
        icon = icon
    )
}