package com.example.cinemalab.presentation.bottomnav.profilescreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.data.remote.dto.ProfileInfoDto
import com.example.cinemalab.domain.usecase.collection.db.DeleteAllUserCollectionsFromDatabaseUseCase
import com.example.cinemalab.domain.usecase.profile.GetProfileInfoUseCase
import com.example.cinemalab.domain.usecase.profile.PostUserAvatarUseCase
import com.example.cinemalab.domain.usecase.storage.email.GetUserEmailFromStorageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val postUserAvatarUseCase: PostUserAvatarUseCase,
    private val deleteAllUserCollectionsFromDatabaseUseCase: DeleteAllUserCollectionsFromDatabaseUseCase,
    private val getUserEmailFromStorageUseCase: GetUserEmailFromStorageUseCase
) : ViewModel() {

    sealed class ProfileState {
        object Initial : ProfileState()
        object Loading : ProfileState()
        class Failure(val errorMessage: String) : ProfileState()
        class Success(val profileInfo: ProfileInfoDto) : ProfileState()
    }

    private val _state = MutableLiveData<ProfileState>(ProfileState.Initial)
    var state: LiveData<ProfileState> = _state

    init {
        getProfileInfo()
    }

    fun exitAlertDialog() {
        _state.value = ProfileState.Initial
    }

    private fun getProfileInfo() {
        viewModelScope.launch {
            _state.value = ProfileState.Loading
            try {
                val data = getProfileInfoUseCase()
                _state.value = ProfileState.Success(data)
            } catch (ex: Exception) {
                _state.value = ProfileState.Failure(ex.message.toString())
            }
        }
    }

    fun loadProfileImage(image: File) {
        _state.value = ProfileState.Loading

        viewModelScope.launch {
            try {
                postUserAvatarUseCase(image)
                val data = getProfileInfoUseCase()
                _state.value = ProfileState.Success(data)
            } catch (ex: Exception) {
                _state.value = ProfileState.Failure(ex.message.toString())
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            val email = getUserEmailFromStorageUseCase()
            deleteAllUserCollectionsFromDatabaseUseCase(email)
        }
    }
}