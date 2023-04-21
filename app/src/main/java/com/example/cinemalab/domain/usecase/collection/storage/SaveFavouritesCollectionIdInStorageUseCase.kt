package com.example.cinemalab.domain.usecase.collection.storage

import android.content.Context
import com.example.cinemalab.common.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SaveFavouritesCollectionIdInStorageUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    operator fun invoke(collectionId: String) {
        val pref =
            context.getSharedPreferences(Constants.PREF_NAME_FAVOURITES_ID, Context.MODE_PRIVATE)
        pref.edit().putString(Constants.FAVOURITES_KEY, collectionId).apply()
    }

}