package com.example.cinemalab.presentation.authscreens.signinscreen

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.R
import com.example.cinemalab.data.remote.dto.AuthTokenPairDto
import com.example.cinemalab.data.remote.dto.LoginDto
import com.example.cinemalab.domain.usecase.LoginUseCase
import com.example.cinemalab.domain.usecase.token.SaveTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val loginUseCase: LoginUseCase
): ViewModel() {

    sealed class SignInState {
        object Initial: SignInState()
        object Loading: SignInState()
        class Failure(val errorMessage: String): SignInState()
        class Success(val tokenResponse: AuthTokenPairDto): SignInState()
    }

    private val _state = MutableLiveData<SignInState>(SignInState.Initial)
    var state: LiveData<SignInState> = _state

    private val _allFieldsIsValid = MutableLiveData(true)
    var allFieldsIsValid: LiveData<Boolean> = _allFieldsIsValid

    private val _validationErrorMessage = MutableLiveData("")
    var validationErrorMessage: LiveData<String> = _validationErrorMessage

    fun exitAlertDialog() {
        _validationErrorMessage.value = ""
        _allFieldsIsValid.value = true
        _state.value = SignInState.Initial
    }

    fun checkIfFieldsIsValid(
        email: String,
        password: String
    ) {
        if (email.isBlank() || password.isBlank()) {
            _validationErrorMessage.value += context.getString(R.string.fill_all_fields)
        }
        _allFieldsIsValid.value = isEmailValid(email) && password.isNotBlank()
    }

    private fun isEmailValid(email: String): Boolean {
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
            _state.value = SignInState.Loading
            try {
                val token = loginUseCase(body)

                val saveTokenUseCase = SaveTokenUseCase(context)
                saveTokenUseCase.execute(token)

                _state.value = SignInState.Success(token)
            } catch(ex: Exception) {
                _state.value = SignInState.Failure(
                    when (ex.message) {
                        context.getString(R.string.exception_http_401) -> context.getString(R.string.invalid_email_or_password)
                        else -> context.getString(R.string.error_something_went_wrong)
                    }
                )
            }
        }
    }

}