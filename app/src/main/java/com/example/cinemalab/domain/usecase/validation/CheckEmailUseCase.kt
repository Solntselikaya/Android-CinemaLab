package com.example.cinemalab.domain.usecase.validation

import android.content.Context
import android.util.Patterns
import com.example.cinemalab.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CheckEmailUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(email: String): String {
        val parsedEmail = email.split("@")
        if (parsedEmail.size == 2){
            val name = email.split("@")[0]
            val domainName = email.split("@")[1]
            val nameValid = name.matches(Regex("^[^A-Z~!@#$%^&+-]+$"))
            val domainNameValid = domainName.matches(Regex("^[^A-Z~!@#$%^&+-]+$"))

            if (email.let { Patterns.EMAIL_ADDRESS.matcher(it).matches()}
                && nameValid
                && domainNameValid
            ) return ""
        }
        return context.getString(R.string.invalid_email_error_message)
    }

}