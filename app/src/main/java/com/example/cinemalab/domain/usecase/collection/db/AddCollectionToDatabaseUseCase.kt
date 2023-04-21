package com.example.cinemalab.domain.usecase.collection.db

import com.example.cinemalab.data.db.dao.CollectionDao
import com.example.cinemalab.data.db.entities.CollectionEntity
import javax.inject.Inject

class AddCollectionToDatabaseUseCase @Inject constructor(
    private val db: CollectionDao
) {

    suspend operator fun invoke(collection: CollectionEntity) {
        db.insert(collection)
    }

}