package com.example.cinemalab.presentation.collectionactions.iconselectionscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IconSelectViewModel @Inject constructor(

) : ViewModel() {

    sealed class IconSelectState {
        object Initial: IconSelectState()
        object Loading: IconSelectState()
        class Failure(val errorMessage: String): IconSelectState()
        object Success: IconSelectState()
    }

    private val _state = MutableLiveData<IconSelectState>(IconSelectState.Initial)
    var state: LiveData<IconSelectState> = _state


}