package com.example.cinemalab.presentation.auth.signupscreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.R
import com.example.cinemalab.common.Constants
import com.example.cinemalab.data.remote.dto.AuthTokenPairDto
import com.example.cinemalab.data.remote.dto.CollectionNameDto
import com.example.cinemalab.data.remote.dto.RegistrationBodyDto
import com.example.cinemalab.data.remote.dto.toCollectionEntity
import com.example.cinemalab.domain.usecase.auth.RegistrationUseCase
import com.example.cinemalab.domain.usecase.collection.api.CreateCollectionUseCase
import com.example.cinemalab.domain.usecase.collection.db.AddCollectionToDatabaseUseCase
import com.example.cinemalab.domain.usecase.storage.email.SaveUserEmailInStorageUseCase
import com.example.cinemalab.domain.usecase.storage.firstrun.GetFirstRunFromStorageUseCase
import com.example.cinemalab.domain.usecase.storage.firstrun.SaveFirstRunInStorageUseCase
import com.example.cinemalab.domain.usecase.token.SaveTokenUseCase
import com.example.cinemalab.domain.usecase.validation.CheckEmailUseCase
import com.example.cinemalab.domain.usecase.validation.CheckEmptyFieldsUseCase
import com.example.cinemalab.domain.usecase.validation.CheckPasswordEqualsUseCase
import com.example.cinemalab.domain.usecase.validation.CheckPasswordLengthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val registrationUseCase: RegistrationUseCase,
    private val createCollectionUseCase: CreateCollectionUseCase,
    private val saveUserEmailInStorageUseCase: SaveUserEmailInStorageUseCase,
    private val addCollectionToDatabaseUseCase: AddCollectionToDatabaseUseCase,
    private val getFirstRunFromStorageUseCase: GetFirstRunFromStorageUseCase,
    private val saveFirstRunInStorageUseCase: SaveFirstRunInStorageUseCase,
    private val checkEmptyFieldsUseCase: CheckEmptyFieldsUseCase,
    private val checkEmailUseCase: CheckEmailUseCase,
    private val checkPasswordLengthUseCase: CheckPasswordLengthUseCase,
    private val checkPasswordEqualsUseCase: CheckPasswordEqualsUseCase
) : ViewModel() {

    sealed class SignUpState {
        object Initial : SignUpState()
        object Loading : SignUpState()
        class Failure(val errorMessage: String) : SignUpState()
        class Success(val tokenResponse: AuthTokenPairDto) : SignUpState()
        object Navigate : SignUpState()
    }

    private val _state = MutableLiveData<SignUpState>(SignUpState.Initial)
    var state: LiveData<SignUpState> = _state

    private val _validationErrorMessage = MutableLiveData("")

    init {
        checkIfFirstRun()
    }

    private fun checkIfFirstRun() {
        if (getFirstRunFromStorageUseCase()) {
            _state.value = SignUpState.Navigate
        } else {
            saveFirstRunInStorageUseCase()
        }
    }

    fun setInitial() {
        _state.value = SignUpState.Initial
    }

    fun exitAlertDialog() {
        _validationErrorMessage.value = ""
        //_allFieldsIsValid.value = true
        _state.value = SignUpState.Initial
    }

    fun checkIfFieldsIsValid(
        name: String,
        surname: String,
        email: String,
        password: String,
        passwordRepeat: String
    ) {
        _validationErrorMessage.value += checkEmptyFieldsUseCase(
            name,
            surname,
            email,
            password,
            passwordRepeat
        )

        if (_validationErrorMessage.value?.isBlank() == true) {
            _validationErrorMessage.value += checkEmailUseCase(email)
            _validationErrorMessage.value += checkPasswordLengthUseCase(password)
            _validationErrorMessage.value += checkPasswordEqualsUseCase(password, passwordRepeat)
        }
    }

    fun register(
        name: String,
        surname: String,
        email: String,
        password: String,
        passwordRepeat: String
    ) {

        checkIfFieldsIsValid(
            name,
            surname,
            email,
            password,
            passwordRepeat
        )
        if (_validationErrorMessage.value?.isNotBlank() == true) {
            _state.value = SignUpState.Failure(_validationErrorMessage.value.toString())
            return
        }

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

                saveUserEmailInStorageUseCase(email)

                val favourites =
                    createCollectionUseCase(CollectionNameDto(context.getString(R.string.favourites)))
                val sharedPrefs = context.getSharedPreferences(
                    Constants.PREF_NAME_FAVOURITES_ID,
                    Context.MODE_PRIVATE
                )
                sharedPrefs.edit().putString(Constants.FAVOURITES_KEY, favourites.collectionId)
                    .apply()

                addCollectionToDatabaseUseCase(favourites.toCollectionEntity(email))

                _state.value = SignUpState.Success(token)
            } catch (ex: Exception) {
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