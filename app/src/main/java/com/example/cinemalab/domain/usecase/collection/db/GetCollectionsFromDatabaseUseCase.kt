package com.example.cinemalab.domain.usecase.collection.db

import com.example.cinemalab.data.db.dao.CollectionDao
import com.example.cinemalab.data.db.entities.CollectionEntity
import com.example.cinemalab.data.db.entities.toCollectionModel
import com.example.cinemalab.domain.model.CollectionModel
import com.example.cinemalab.domain.model.toCollectionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionsFromDatabaseUseCase @Inject constructor(
    private val db: CollectionDao
) {

    operator fun invoke(): List<CollectionEntity> {
        return db.getAll()
    }

}