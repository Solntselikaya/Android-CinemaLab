package com.example.cinemalab.domain.usecase.validation

import android.content.Context
import com.example.cinemalab.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CheckPasswordLengthUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        const val MIN_LENGTH = 6
    }

    operator fun invoke(password: String): String {
        return if (MIN_LENGTH > password.length)
            context.getString(R.string.password_too_short)
        else
            ""
    }

}