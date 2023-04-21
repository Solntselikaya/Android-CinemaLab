package com.example.cinemalab.presentation.collectionactions.edit.collectiondetailsscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.data.remote.dto.toMovieModel
import com.example.cinemalab.domain.model.MovieModel
import com.example.cinemalab.domain.usecase.collection.api.GetCollectionMoviesUseCase
import com.example.cinemalab.domain.usecase.collection.storage.GetFavouritesCollectionIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionDetailsViewModel @Inject constructor(
    private val getCollectionMoviesUseCase: GetCollectionMoviesUseCase,
    private val getFavouritesCollectionIdUseCase: GetFavouritesCollectionIdUseCase
) : ViewModel() {

    sealed class CollectionDetailsState {
        object Initial : CollectionDetailsState()
        object Loading : CollectionDetailsState()
        class Failure(val errorMessage: String) : CollectionDetailsState()
        class Success(val movies: List<MovieModel>) : CollectionDetailsState()
    }

    private val _state = MutableLiveData<CollectionDetailsState>(CollectionDetailsState.Initial)
    var state: LiveData<CollectionDetailsState> = _state

    fun getCollectionMovies(collectionId: String) {

        _state.value = CollectionDetailsState.Loading

        viewModelScope.launch {
            try {
                val movies = getCollectionMoviesUseCase(collectionId)
                _state.value = CollectionDetailsState.Success(movies.map { it.toMovieModel() })
            } catch (ex: Exception) {
                _state.value = CollectionDetailsState.Failure(ex.message.toString())
            }
        }
    }

    fun isCollectionFavourites(collectionId: String): Boolean {
        return getFavouritesCollectionIdUseCase() == collectionId
    }
}