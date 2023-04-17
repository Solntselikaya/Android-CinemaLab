package com.example.cinemalab.domain.usecase.collection.api

import android.content.Context
import com.example.cinemalab.common.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetFavouritesCollectionIdUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    operator fun invoke(): String {
        val pref =
            context.getSharedPreferences(Constants.PREF_NAME_FAVOURITES_ID, Context.MODE_PRIVATE)

        return pref.getString(Constants.FAVOURITES_KEY, "").toString()
    }

}