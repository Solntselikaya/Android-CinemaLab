package com.example.cinemalab.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cinemalab.R
import com.example.cinemalab.common.Constants.DATABASE_NAME
import com.example.cinemalab.domain.model.CollectionModel

@Entity(tableName = DATABASE_NAME)
data class CollectionEntity(
    @PrimaryKey
    @ColumnInfo(name = "collection_id")
    val collectionId: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "icon")
    val icon: Int? = R.drawable.collection_icon_01,
    @ColumnInfo(name = "user_id")
    val userId: String? = null
)

fun CollectionEntity.toCollectionModel(): CollectionModel {
    return CollectionModel(
        id = collectionId,
        name = name,
        icon = icon
    )
}