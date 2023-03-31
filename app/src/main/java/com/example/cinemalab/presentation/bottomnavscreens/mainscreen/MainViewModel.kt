package com.example.cinemalab.presentation.bottomnavscreens.mainscreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.R
import com.example.cinemalab.data.remote.dto.MovieDto
import com.example.cinemalab.data.remote.dto.PromotedCoverDto
import com.example.cinemalab.data.remote.dto.toMovieModel
import com.example.cinemalab.domain.model.Filters
import com.example.cinemalab.domain.model.MovieModel
import com.example.cinemalab.domain.usecase.cover.GetPromotedBannerUseCase
import com.example.cinemalab.domain.usecase.movie.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getPromotedBannerUseCase: GetPromotedBannerUseCase,
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    sealed class MainState {
        object Initial: MainState()
        object Loading: MainState()
        class Failure(val errorMessage: String): MainState()
        class Success(
            val promotedCover: PromotedCoverDto,
            val moviesTrend: List<MovieDto>,
            //val lastWatchedMovie: MovieDto,
            val moviesNew: List<MovieDto>,
            val moviesForMe: List<MovieDto>
        ): MainState()
    }

    private val _state = MutableLiveData<MainState>(MainState.Initial)
    var state: LiveData<MainState> = _state

    init {
        getAllMovies()
    }

    private fun getAllMovies() {

        _state.value = MainState.Loading

        viewModelScope.launch {
            try {
                val cover = getPromotedBannerUseCase()
                val moviesTrend = getMoviesUseCase(Filters.inTrend.name)
                val lastWatchedMovie = getMoviesUseCase(Filters.lastView.name)
                val moviesNew = getMoviesUseCase(Filters.new.name)
                val moviesForMe = getMoviesUseCase(Filters.forMe.name)

                _state.value = MainState.Success(
                    promotedCover = cover,
                    moviesTrend = moviesTrend,
                    //lastWatchedMovie = lastWatchedMovie.last(),
                    moviesNew = moviesNew,
                    moviesForMe = moviesForMe
                )
            } catch(ex: Exception) {
                //_state.value = MainState.Failure(context.getString(R.string.error_something_went_wrong))
                _state.value = MainState.Failure(ex.message.toString())
                //return@launch
            }
        }
    }
}