package com.example.cinemalab.presentation.chats.messenger

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessengerViewModel @Inject constructor(

) : ViewModel() {

    sealed class MessengerState {
        object Initial: MessengerState()
        object Loading: MessengerState()
        class Failure(val errorMessage: String): MessengerState()
        object Success: MessengerState()
    }

    private val _state = MutableLiveData<MessengerState>(MessengerState.Initial)
    var state: LiveData<MessengerState> = _state

}