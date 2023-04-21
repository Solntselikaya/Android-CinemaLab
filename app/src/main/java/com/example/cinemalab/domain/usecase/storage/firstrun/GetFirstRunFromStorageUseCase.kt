package com.example.cinemalab.domain.usecase.storage.firstrun

import android.content.Context
import com.example.cinemalab.common.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetFirstRunFromStorageUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    operator fun invoke(): Boolean {
        val pref =
            context.getSharedPreferences(Constants.PREF_NAME_FIRST_RUN, Context.MODE_PRIVATE)

        return pref.getBoolean(Constants.FIRST_RUN_KEY, false)
    }

}