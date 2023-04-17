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
): Parcelable

fun CollectionModel.toCollectionEntity(): CollectionEntity {
    return CollectionEntity(
        id = id,
        name = name,
        icon = icon
    )
}