package com.example.cinemalab.presentation.bottomnavscreens.profilescreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.data.remote.dto.ProfileInfoDto
import com.example.cinemalab.domain.usecase.profile.GetProfileInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getProfileInfoUseCase: GetProfileInfoUseCase
) : ViewModel() {

    sealed class ProfileState {
        object Initial: ProfileState()
        object Loading: ProfileState()
        class Failure(val errorMessage: String): ProfileState()
        class Success(val profileInfo: ProfileInfoDto): ProfileState()
    }

    private val _state = MutableLiveData<ProfileState>(ProfileState.Initial)
    var state: LiveData<ProfileState> = _state

    fun exitAlertDialog() {
        _state.value = ProfileState.Initial
    }

    fun getProfileInfo() {
        viewModelScope.launch {
            _state.value = ProfileState.Loading
            try {
                val data = getProfileInfoUseCase()
                _state.value = ProfileState.Success(data)
            } catch(ex: Exception) {
                _state.value = ProfileState.Failure(ex.message.toString())
            }
        }
    }
}