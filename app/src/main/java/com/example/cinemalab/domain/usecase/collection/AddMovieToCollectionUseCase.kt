package com.example.cinemalab.domain.usecase.collection

import android.content.Context
import com.example.cinemalab.domain.repository.CollectionRepository
import com.example.cinemalab.domain.usecase.token.GetTokenFromLocalStorageUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AddMovieToCollectionUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: CollectionRepository
) {

    suspend operator fun invoke(collectionId: String, movieId: String) {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        val bearerToken = "Bearer ${token.accessToken}"

        return repository.addMovieToCollection(bearerToken, collectionId, movieId)
    }

}