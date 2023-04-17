package com.example.cinemalab.presentation.collectionactions.edit.editcollectionscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.domain.usecase.collection.api.DeleteCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCollectionViewModel @Inject constructor(
    private val deleteCollectionUseCase: DeleteCollectionUseCase
) : ViewModel() {

    sealed class EditCollectionState {
        object Initial: EditCollectionState()
        object Loading: EditCollectionState()
        class Failure(val errorMessage: String): EditCollectionState()
        object Success: EditCollectionState()
    }

    private val _state = MutableLiveData<EditCollectionState>(EditCollectionState.Initial)
    var state: LiveData<EditCollectionState> = _state

    fun saveEditedCollection() {

    }

    fun deleteCollection(collectionId: String) {

        _state.value = EditCollectionState.Loading

        viewModelScope.launch {
            try {
                deleteCollectionUseCase(collectionId)
                _state.value = EditCollectionState.Success
            } catch(ex: Exception) {
                _state.value = EditCollectionState.Failure(ex.message.toString())
            }
        }
    }
}