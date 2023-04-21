package com.example.cinemalab.domain.usecase.storage

import android.content.Context
import com.example.cinemalab.common.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetUserEmailFromStorageUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    operator fun invoke(): String {
        val pref =
            context.getSharedPreferences(Constants.PREF_NAME_USER_EMAIL, Context.MODE_PRIVATE)

        return pref.getString(Constants.USER_EMAIL_KEY, "").toString()
    }

}