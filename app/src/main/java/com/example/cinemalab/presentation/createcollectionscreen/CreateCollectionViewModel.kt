package com.example.cinemalab.presentation.createcollectionscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.data.remote.dto.CollectionNameDto
import com.example.cinemalab.domain.usecase.collection.CreateCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCollectionViewModel @Inject constructor(
    private val createCollectionUseCase: CreateCollectionUseCase
) : ViewModel() {

    sealed class CreateCollectionState {
        object Initial: CreateCollectionState()
        object Loading: CreateCollectionState()
        object Success: CreateCollectionState()
        class Failure(val errorMessage: String): CreateCollectionState()
    }

    private val _state = MutableLiveData<CreateCollectionState>(CreateCollectionState.Initial)
    var state: LiveData<CreateCollectionState> = _state

    fun createCollection(name: String) {

        _state.value = CreateCollectionState.Loading

        viewModelScope.launch {
            try {
                createCollectionUseCase(CollectionNameDto(name))
                _state.value = CreateCollectionState.Success
            } catch(ex: Exception) {
                _state.value = CreateCollectionState.Failure(ex.message.toString())
            }
        }
    }
}