package com.example.cinemalab.domain.usecase.collection.db

import android.content.Context
import com.example.cinemalab.data.db.dao.CollectionDao
import com.example.cinemalab.data.db.entities.CollectionEntity
import com.example.cinemalab.data.remote.dto.MovieIdDto
import com.example.cinemalab.domain.model.CollectionModel
import com.example.cinemalab.domain.model.toCollectionEntity
import com.example.cinemalab.domain.repository.CollectionRepository
import com.example.cinemalab.domain.usecase.token.GetTokenFromLocalStorageUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DeleteCollectionFromDatabaseUseCase @Inject constructor(
    private val db: CollectionDao
) {

    suspend operator fun invoke(collection: CollectionEntity) {
        db.delete(collection)
    }

}