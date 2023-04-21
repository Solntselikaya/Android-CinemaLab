package com.example.cinemalab.domain.usecase.storage.email

import android.content.Context
import com.example.cinemalab.common.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SaveUserEmailInStorageUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    operator fun invoke(userEmail: String) {
        val pref =
            context.getSharedPreferences(Constants.PREF_NAME_USER_EMAIL, Context.MODE_PRIVATE)
        pref.edit().putString(Constants.USER_EMAIL_KEY, userEmail).apply()
    }

}