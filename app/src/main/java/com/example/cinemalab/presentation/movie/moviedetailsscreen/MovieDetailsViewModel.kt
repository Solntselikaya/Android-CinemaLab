package com.example.cinemalab.presentation.movie.moviedetailsscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(

) : ViewModel() {

    sealed class MovieDetailsState {
        object Initial: MovieDetailsState()
        object Loading: MovieDetailsState()
        class Failure(val errorMessage: String): MovieDetailsState()
        object Success: MovieDetailsState()
    }

    private val _state = MutableLiveData<MovieDetailsState>(MovieDetailsState.Initial)
    var state: LiveData<MovieDetailsState> = _state
}