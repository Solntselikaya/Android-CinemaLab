package com.example.cinemalab.presentation.bottomnavscreens.collectionsscreen

import androidx.lifecycle.ViewModel
import com.example.cinemalab.domain.model.CollectionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
) : ViewModel() {

    sealed class CollectionsState {
        object Initial: CollectionsState()
        object Loading: CollectionsState()
        class Failure(val errorMessage: String): CollectionsState()
        class Success(val collections: List<CollectionModel>): CollectionsState()
    }

}