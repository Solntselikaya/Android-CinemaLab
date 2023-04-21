package com.example.cinemalab.presentation.collectionactions.edit.editcollectionscreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.R
import com.example.cinemalab.common.Constants
import com.example.cinemalab.data.db.entities.CollectionEntity
import com.example.cinemalab.domain.model.CollectionModel
import com.example.cinemalab.domain.model.toCollectionEntity
import com.example.cinemalab.domain.usecase.collection.api.DeleteCollectionUseCase
import com.example.cinemalab.domain.usecase.collection.db.DeleteCollectionFromDatabaseUseCase
import com.example.cinemalab.domain.usecase.collection.db.UpdateCollectionInDatabaseUseCase
import com.example.cinemalab.domain.usecase.storage.email.GetUserEmailFromStorageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCollectionViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val deleteCollectionUseCase: DeleteCollectionUseCase,
    private val deleteCollectionFromDatabaseUseCase: DeleteCollectionFromDatabaseUseCase,
    private val updateCollectionInDatabaseUseCase: UpdateCollectionInDatabaseUseCase,
    private val getUserEmailFromStorageUseCase: GetUserEmailFromStorageUseCase
) : ViewModel() {

    sealed class EditCollectionState {
        object Initial : EditCollectionState()
        object Loading : EditCollectionState()
        class Failure(val errorMessage: String) : EditCollectionState()
        object Success : EditCollectionState()
        object Quit : EditCollectionState()
    }

    private val _state = MutableLiveData<EditCollectionState>(EditCollectionState.Initial)
    var state: LiveData<EditCollectionState> = _state

    fun saveEditedCollection(collection: CollectionModel?) {
        if (collection?.name == context.getString(Constants.RESERVED_NAME_FAVOURITES)) {
            _state.value = EditCollectionState.Failure(
                context.getString(R.string.try_to_use_reserved_name_error_message)
            )
            return
        }

        if (collection?.name == "") {
            _state.value = EditCollectionState.Failure(
                context.getString(R.string.try_to_use_empty_collection_name_error_message)
            )
            return
        }

        _state.value = EditCollectionState.Loading

        viewModelScope.launch {
            try {
                val email = getUserEmailFromStorageUseCase()
                updateCollectionInDatabaseUseCase(
                    collection?.toCollectionEntity(email) ?: CollectionEntity("", "")
                )
                _state.value = EditCollectionState.Success
            } catch (ex: Exception) {
                _state.value = EditCollectionState.Failure(ex.message.toString())
            }
        }
    }

    fun deleteCollection(collectionId: String) {

        _state.value = EditCollectionState.Loading

        viewModelScope.launch {
            try {
                deleteCollectionUseCase(collectionId)
                deleteCollectionFromDatabaseUseCase(collectionId)
                _state.value = EditCollectionState.Quit
            } catch (ex: Exception) {
                _state.value = EditCollectionState.Failure(ex.message.toString())
            }
        }
    }
}