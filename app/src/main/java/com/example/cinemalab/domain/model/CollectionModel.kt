package com.example.cinemalab.domain.model

import android.os.Parcelable
import com.example.cinemalab.R
import com.example.cinemalab.data.db.entities.CollectionEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class CollectionModel(
    val id: String,
    val name: String,
    val icon: Int? = R.drawable.collection_icon_01
) : Parcelable

fun CollectionModel.toCollectionEntity(userId: String): CollectionEntity {
    return CollectionEntity(
        collectionId = id,
        name = name,
        icon = icon,
        userId = userId
    )
}