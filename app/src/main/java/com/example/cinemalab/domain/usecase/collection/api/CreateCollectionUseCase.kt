package com.example.cinemalab.domain.usecase.collection.api

import android.content.Context
import com.example.cinemalab.data.remote.dto.CollectionDto
import com.example.cinemalab.data.remote.dto.CollectionNameDto
import com.example.cinemalab.domain.repository.CollectionRepository
import com.example.cinemalab.domain.usecase.token.GetTokenFromLocalStorageUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CreateCollectionUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: CollectionRepository
) {

    suspend operator fun invoke(collectionName: CollectionNameDto): CollectionDto {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        val bearerToken = "Bearer ${token.accessToken}"

        return repository.post(bearerToken, collectionName)
    }

}