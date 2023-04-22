package com.example.cinemalab.domain.usecase.validation

import android.content.Context
import com.example.cinemalab.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CheckPasswordEqualsUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    operator fun invoke(password: String, repeat: String): String {
        return if (password == repeat)
            ""
        else
            context.getString(R.string.passwords_are_not_equel)
    }

}