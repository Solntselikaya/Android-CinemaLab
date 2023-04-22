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

fun CollectionDto.toCollectionEntity(
    userId: String,
    icon: Int? = R.drawable.collection_icon_01
): CollectionEntity {
    return CollectionEntity(
        collectionId = collectionId,
        name = name,
        icon = icon,
        userId = userId
    )
}