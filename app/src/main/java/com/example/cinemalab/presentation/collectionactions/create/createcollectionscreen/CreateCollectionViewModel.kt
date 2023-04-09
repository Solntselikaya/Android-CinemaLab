package com.example.cinemalab.presentation.collectionactions.create.createcollectionscreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.R
import com.example.cinemalab.common.Constants
import com.example.cinemalab.data.remote.dto.CollectionNameDto
import com.example.cinemalab.domain.usecase.collection.CreateCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCollectionViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
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

        if(name == context.getString(Constants.RESERVED_NAME_FAVOURITES)) {
            _state.value = CreateCollectionState.Failure(context.getString(R.string.try_to_use_reserved_name_error_message))
            return
        }

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