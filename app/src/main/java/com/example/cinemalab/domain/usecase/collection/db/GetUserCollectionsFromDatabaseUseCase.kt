package com.example.cinemalab.domain.usecase.collection.db

import com.example.cinemalab.data.db.dao.CollectionDao
import com.example.cinemalab.data.db.entities.CollectionEntity
import com.example.cinemalab.domain.usecase.storage.GetUserEmailFromStorageUseCase
import javax.inject.Inject

class GetUserCollectionsFromDatabaseUseCase @Inject constructor(
    private val db: CollectionDao,
    private val getUserEmailFromStorageUseCase: GetUserEmailFromStorageUseCase
) {

    operator fun invoke(): List<CollectionEntity> {
        val userEmail = getUserEmailFromStorageUseCase()
        return db.getAllForUser(userEmail)
    }

}