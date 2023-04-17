package com.example.cinemalab.presentation.collectionactions.create.createcollectionscreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.R
import com.example.cinemalab.common.Constants
import com.example.cinemalab.data.db.entities.CollectionEntity
import com.example.cinemalab.data.remote.dto.CollectionNameDto
import com.example.cinemalab.data.remote.dto.toCollectionEntity
import com.example.cinemalab.domain.usecase.collection.api.CreateCollectionUseCase
import com.example.cinemalab.domain.usecase.collection.db.AddCollectionToDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCollectionViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val createCollectionUseCase: CreateCollectionUseCase,
    private val addCollectionToDatabaseUseCase: AddCollectionToDatabaseUseCase
) : ViewModel() {

    sealed class CreateCollectionState {
        object Initial: CreateCollectionState()
        object Loading: CreateCollectionState()
        object Success: CreateCollectionState()
        class Failure(val errorMessage: String): CreateCollectionState()
    }

    private val _state = MutableLiveData<CreateCollectionState>(CreateCollectionState.Initial)
    var state: LiveData<CreateCollectionState> = _state

    private var dbIsNotReady: Boolean = true

    fun createCollection(
        name: String,
        icon: Int?
    ) {

        if(name == context.getString(Constants.RESERVED_NAME_FAVOURITES)) {
            _state.value = CreateCollectionState.Failure(context.getString(R.string.try_to_use_reserved_name_error_message))
            return
        }

        if(name.isBlank()) {
            _state.value = CreateCollectionState.Failure(context.getString(R.string.try_to_use_empty_collection_name_error_message))
            return
        }

        _state.value = CreateCollectionState.Loading

        viewModelScope.launch {
            try {
                val newCollection = createCollectionUseCase(CollectionNameDto(name))
                addToDatabase(newCollection.toCollectionEntity(icon))
                while (dbIsNotReady) {}
                _state.value = CreateCollectionState.Success
            } catch(ex: Exception) {
                _state.value = CreateCollectionState.Failure(ex.message.toString())
            }
        }
    }

    private fun addToDatabase(collection: CollectionEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            addCollectionToDatabaseUseCase(collection)
            dbIsNotReady = false
        }
    }
}