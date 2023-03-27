package com.example.cinemalab.presentation.signin

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.R
import com.example.cinemalab.data.remote.dto.LoginDto
import com.example.cinemalab.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val loginUseCase: LoginUseCase
): ViewModel() {

    private val _allFieldsIsValid = MutableLiveData(true)
    var allFieldsIsValid: LiveData<Boolean> = _allFieldsIsValid

    private val _validationErrorMessage = MutableLiveData("")
    var validationErrorMessage: LiveData<String> = _validationErrorMessage

    fun clearErrorMessage() {
        _validationErrorMessage.value = ""
    }

    fun checkIfFieldsIsValid(
        email: String,
        password: String
    ) {
        if (email.isBlank() || password.isBlank()) {
            _validationErrorMessage.value += context.getString(R.string.fill_all_fields)
        }
        _allFieldsIsValid.value = checkIfEmailIsValid(email) && password.isNotBlank()
    }

    private fun checkIfEmailIsValid(email: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            _validationErrorMessage.value += context.getString(R.string.invalid_email_error_message)
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun login(email: String, password: String) {

        val body = LoginDto(
            email = email,
            password = password
        )

        viewModelScope.launch {
            try {
                val token = loginUseCase(body)

            } catch(ex: Exception) {
                //TODO
            }
        }
    }

}