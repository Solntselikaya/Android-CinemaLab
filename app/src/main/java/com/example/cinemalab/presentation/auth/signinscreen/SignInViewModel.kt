package com.example.cinemalab.presentation.auth.signinscreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.R
import com.example.cinemalab.common.Constants
import com.example.cinemalab.data.remote.dto.AuthTokenPairDto
import com.example.cinemalab.data.remote.dto.CollectionDto
import com.example.cinemalab.data.remote.dto.LoginDto
import com.example.cinemalab.data.remote.dto.toCollectionEntity
import com.example.cinemalab.domain.usecase.auth.LoginUseCase
import com.example.cinemalab.domain.usecase.collection.api.GetCollectionsUseCase
import com.example.cinemalab.domain.usecase.collection.db.AddCollectionToDatabaseUseCase
import com.example.cinemalab.domain.usecase.collection.storage.GetFavouritesCollectionIdUseCase
import com.example.cinemalab.domain.usecase.collection.storage.SaveFavouritesCollectionIdInStorageUseCase
import com.example.cinemalab.domain.usecase.storage.email.SaveUserEmailInStorageUseCase
import com.example.cinemalab.domain.usecase.token.SaveTokenUseCase
import com.example.cinemalab.domain.usecase.validation.CheckEmailUseCase
import com.example.cinemalab.domain.usecase.validation.CheckEmptyFieldsUseCase
import com.example.cinemalab.domain.usecase.validation.CheckPasswordLengthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val loginUseCase: LoginUseCase,
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val getFavouritesCollectionIdUseCase: GetFavouritesCollectionIdUseCase,
    private val saveFavouritesCollectionIdInStorageUseCase: SaveFavouritesCollectionIdInStorageUseCase,
    private val saveUserEmailInStorageUseCase: SaveUserEmailInStorageUseCase,
    private val addCollectionToDatabaseUseCase: AddCollectionToDatabaseUseCase,
    private val checkEmailUseCase: CheckEmailUseCase,
    private val checkPasswordLengthUseCase: CheckPasswordLengthUseCase,
    private val checkEmptyFieldsUseCase: CheckEmptyFieldsUseCase
) : ViewModel() {

    sealed class SignInState {
        object Initial : SignInState()
        object Loading : SignInState()
        class Failure(val errorMessage: String) : SignInState()
        class Success(val tokenResponse: AuthTokenPairDto) : SignInState()
    }

    private val _state = MutableLiveData<SignInState>(SignInState.Initial)
    var state: LiveData<SignInState> = _state

    private val _validationErrorMessage = MutableLiveData("")

    fun exitAlertDialog() {
        _validationErrorMessage.value = ""
        _state.value = SignInState.Initial
    }

    private fun checkIfFieldsIsValid(
        email: String,
        password: String
    ) {
        _validationErrorMessage.value += checkEmptyFieldsUseCase(email, password)

        if (_validationErrorMessage.value?.isBlank() == true) {
            _validationErrorMessage.value += checkEmailUseCase(email)
            _validationErrorMessage.value += checkPasswordLengthUseCase(password)
        }
    }

    fun login(email: String, password: String) {

        checkIfFieldsIsValid(email, password)
        if (_validationErrorMessage.value?.isNotBlank() == true) {
            _state.value = SignInState.Failure(_validationErrorMessage.value.toString())
            return
        }

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

                saveUserEmailInStorageUseCase(email)

                val collections = getCollectionsUseCase()
                saveFavId(collections)
                rearrangeAndSaveToDbCollections(collections, email)

                _state.value = SignInState.Success(token)
            } catch (ex: Exception) {
                _state.value = SignInState.Failure(
                    when (ex.message) {
                        context.getString(R.string.exception_http_401) -> context.getString(R.string.invalid_email_or_password)
                        else -> context.getString(R.string.error_something_went_wrong)
                    }
                )
            }
        }
    }

    private fun saveFavId(collections: List<CollectionDto>) {
        val favString = context.getString(Constants.RESERVED_NAME_FAVOURITES)
        val favCollection = collections.find { it.name == favString }
        saveFavouritesCollectionIdInStorageUseCase(favCollection?.collectionId ?: "")
    }

    private suspend fun rearrangeAndSaveToDbCollections(
        collections: List<CollectionDto>,
        email: String
    ) {
        val favId = getFavouritesCollectionIdUseCase()

        val rearrangedCollections = mutableListOf<CollectionDto>()
        val favCollection = collections.find { it.collectionId == favId }
        if (favCollection != null) {
            rearrangedCollections.add(favCollection)
            addCollectionToDatabaseUseCase(favCollection.toCollectionEntity(email))
        }
        for (i in collections.indices) {
            if (collections[i].collectionId == favId)
                continue
            else {
                rearrangedCollections.add(collections[i])
                addCollectionToDatabaseUseCase(collections[i].toCollectionEntity(email))
            }
        }
    }

    fun start() {
        _state.value = SignInState.Initial
    }
}