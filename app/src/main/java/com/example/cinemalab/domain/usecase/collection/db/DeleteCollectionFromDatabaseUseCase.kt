package com.example.cinemalab.domain.usecase.collection.db

import com.example.cinemalab.data.db.dao.CollectionDao
import javax.inject.Inject

class DeleteCollectionFromDatabaseUseCase @Inject constructor(
    private val db: CollectionDao
) {

    suspend operator fun invoke(collectionId: String) {
        db.delete(collectionId)
    }

}