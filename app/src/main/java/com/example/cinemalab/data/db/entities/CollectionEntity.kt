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
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "icon")
    val icon: Int? = R.drawable.collection_icon_01
)

fun CollectionEntity.toCollectionModel(): CollectionModel {
    return CollectionModel(
        id = id,
        name = name,
        icon = icon
    )
}