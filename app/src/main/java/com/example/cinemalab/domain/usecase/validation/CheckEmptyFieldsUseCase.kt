package com.example.cinemalab.domain.usecase.validation

import android.content.Context
import com.example.cinemalab.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CheckEmptyFieldsUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    operator fun invoke(vararg values: String): String {
        values.forEach {
            if (it.isBlank())
                return context.getString(R.string.fill_all_fields)
        }
        return ""
    }

}