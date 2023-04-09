package com.example.cinemalab.presentation.bottomnav.collectionsscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.data.remote.dto.CollectionDto
import com.example.cinemalab.data.remote.dto.toCollectionModel
import com.example.cinemalab.domain.model.CollectionModel
import com.example.cinemalab.domain.usecase.collection.CreateCollectionUseCase
import com.example.cinemalab.domain.usecase.collection.GetCollectionMoviesUseCase
import com.example.cinemalab.domain.usecase.collection.GetCollectionsUseCase
import com.example.cinemalab.domain.usecase.collection.GetFavouritesCollectionIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val getFavouritesCollectionIdUseCase: GetFavouritesCollectionIdUseCase
) : ViewModel() {

    sealed class CollectionsState {
        object Initial: CollectionsState()
        object Loading: CollectionsState()
        class Failure(val errorMessage: String): CollectionsState()
        class Success(val collections: List<CollectionModel>): CollectionsState()
    }

    private val _state = MutableLiveData<CollectionsState>(CollectionsState.Initial)
    var state: LiveData<CollectionsState> = _state

    /*init {
        getCollections()
    }*/

    fun getCollections() {

        _state.value = CollectionsState.Loading

        viewModelScope.launch {
            try {
                val collections = getCollectionsUseCase()

                _state.value = CollectionsState.Success(
                    getRearrangedCollections(collections)
                )
            } catch(ex: Exception) {
                _state.value = CollectionsState.Failure(ex.message.toString())
            }
        }
    }

    private fun getRearrangedCollections(collections: List<CollectionDto>): List<CollectionModel> {
        val favId = getFavouritesCollectionIdUseCase()

        val rearrangedCollections = mutableListOf<CollectionDto>()
        val favCollection = collections.find { it.collectionId == favId }
        if (favCollection != null) {
            rearrangedCollections.add(favCollection)
        }
        for(i in collections.indices){
            if (collections[i].collectionId == favId)
                continue
            else
                rearrangedCollections.add(collections[i])
        }

        return rearrangedCollections.map { it.toCollectionModel() }
    }

}