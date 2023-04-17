package com.example.cinemalab.presentation.auth.signupscreen

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.R
import com.example.cinemalab.common.Constants
import com.example.cinemalab.data.remote.dto.AuthTokenPairDto
import com.example.cinemalab.data.remote.dto.CollectionNameDto
import com.example.cinemalab.data.remote.dto.RegistrationBodyDto
import com.example.cinemalab.domain.usecase.auth.RegistrationUseCase
import com.example.cinemalab.domain.usecase.collection.api.CreateCollectionUseCase
import com.example.cinemalab.domain.usecase.token.SaveTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val registrationUseCase: RegistrationUseCase,
    private val createCollectionUseCase: CreateCollectionUseCase
): ViewModel() {

    sealed class SignUpState {
        object Initial: SignUpState()
        object Loading: SignUpState()
        class Failure(val errorMessage: String): SignUpState()
        class Success(val tokenResponse: AuthTokenPairDto): SignUpState()
    }

    private val _state = MutableLiveData<SignUpState>(SignUpState.Initial)
    var state: LiveData<SignUpState> = _state

    private val _allFieldsIsValid = MutableLiveData(true)
    var allFieldsIsValid: LiveData<Boolean> = _allFieldsIsValid

    private val _validationErrorMessage = MutableLiveData("")
    var validationErrorMessage: LiveData<String> = _validationErrorMessage

    fun exitAlertDialog() {
        _validationErrorMessage.value = ""
        _allFieldsIsValid.value = true
        _state.value = SignUpState.Initial
    }

    fun checkIfFieldsIsValid(
        name: String,
        surname: String,
        email: String,
        password: String,
        passwordRepeat: String
    ) {
        _allFieldsIsValid.value =
                isSomeFieldsEmpty(name, surname, email, password)
                && isEmailValid(email)
                && isPasswordValid(password, passwordRepeat)

        if(_allFieldsIsValid.value == false)
            _state.value = SignUpState.Failure(_validationErrorMessage.value!!)
    }

    private fun isSomeFieldsEmpty(
        name: String,
        surname: String,
        email: String,
        password: String,
    ): Boolean {
        if (name.isBlank()
            || surname.isBlank()
            || email.isBlank()
            || password.isBlank()
        ) _validationErrorMessage.value += context.getString(R.string.fill_all_fields)

        return name.isNotBlank()
                && surname.isNotBlank()
                && email.isNotBlank()
                && password.isNotBlank()
    }

    private fun isEmailValid(email: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _validationErrorMessage.value += context.getString(R.string.invalid_email_error_message)
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(
        password: String,
        passwordRepeat: String
    ): Boolean {
        if (password != passwordRepeat) {
            _validationErrorMessage.value += context.getString(R.string.passwords_are_not_equel)
        }
        return password == passwordRepeat
    }

    fun register(
        name: String,
        surname: String,
        email: String,
        password: String
    ) {

        val body = RegistrationBodyDto(
            email = email,
            firstName = name,
            lastName = surname,
            password = password
        )

        viewModelScope.launch {
            _state.value = SignUpState.Loading
            try {
                val token = registrationUseCase(body)

                val saveTokenUseCase = SaveTokenUseCase(context)
                saveTokenUseCase.execute(token)
                
                val favourites = createCollectionUseCase(CollectionNameDto(context.getString(R.string.favourites)))
                val sharedPrefs = context.getSharedPreferences(Constants.PREF_NAME_FAVOURITES_ID, Context.MODE_PRIVATE)
                sharedPrefs.edit().putString(Constants.FAVOURITES_KEY, favourites.collectionId).apply()

                _state.value = SignUpState.Success(token)
            } catch(ex: Exception) {
                _state.value = SignUpState.Failure(
                    when (ex.message) {
                        context.getString(R.string.exception_http_409) -> context.getString(R.string.error_user_already_exists)
                        else -> context.getString(R.string.error_something_went_wrong)
                    }
                )
            }
        }
    }
}