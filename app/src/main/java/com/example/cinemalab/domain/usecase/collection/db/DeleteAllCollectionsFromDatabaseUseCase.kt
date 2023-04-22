package com.example.cinemalab.domain.usecase.collection.db

import com.example.cinemalab.data.db.dao.CollectionDao
import javax.inject.Inject

class DeleteAllCollectionsFromDatabaseUseCase @Inject constructor(
    private val db: CollectionDao
) {

    suspend operator fun invoke() {
        db.deleteAll()
    }

}