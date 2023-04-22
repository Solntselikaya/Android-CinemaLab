package com.example.cinemalab.presentation.bottomnav.mainscreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.data.remote.dto.toHistoryModel
import com.example.cinemalab.data.remote.dto.toMovieModel
import com.example.cinemalab.data.remote.dto.toPromotedCoverModel
import com.example.cinemalab.domain.model.Filters
import com.example.cinemalab.domain.model.HistoryModel
import com.example.cinemalab.domain.model.MovieModel
import com.example.cinemalab.domain.model.PromotedCoverModel
import com.example.cinemalab.domain.usecase.cover.GetPromotedBannerUseCase
import com.example.cinemalab.domain.usecase.history.GetHistoryUseCase
import com.example.cinemalab.domain.usecase.movie.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getPromotedBannerUseCase: GetPromotedBannerUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {

    sealed class MainState {
        object Initial : MainState()
        object Loading : MainState()
        class Failure(val errorMessage: String) : MainState()
        class Success(
            val promotedCover: PromotedCoverModel,
            val moviesTrend: List<MovieModel>,
            val lastWatchedMovie: List<MovieModel>,
            val moviesNew: List<MovieModel>,
            val moviesForMe: List<MovieModel>,
            val lastWatchedEpisodes: List<HistoryModel>
        ) : MainState()
    }

    private val _state = MutableLiveData<MainState>(MainState.Initial)
    var state: LiveData<MainState> = _state

    /*init {
        getAllMovies()
    }*/

    fun getAllMovies() {

        _state.value = MainState.Loading

        viewModelScope.launch {
            try {
                val cover = getPromotedBannerUseCase()
                val moviesTrend = getMoviesUseCase(Filters.inTrend.name)
                val lastWatchedMovie = getMoviesUseCase(Filters.lastView.name)
                val moviesNew = getMoviesUseCase(Filters.new.name)
                val moviesForMe = getMoviesUseCase(Filters.forMe.name)
                val lastWatchedEpisodes = getHistoryUseCase()

                _state.value = MainState.Success(
                    promotedCover = cover.toPromotedCoverModel(),
                    moviesTrend = moviesTrend.map { it.toMovieModel() },
                    lastWatchedMovie = lastWatchedMovie.map { it.toMovieModel() },
                    moviesNew = moviesNew.map { it.toMovieModel() },
                    moviesForMe = moviesForMe.map { it.toMovieModel() },
                    lastWatchedEpisodes = lastWatchedEpisodes.map { it.toHistoryModel() }
                )
            } catch (ex: Exception) {
                //_state.value = MainState.Failure(context.getString(R.string.error_something_went_wrong))
                _state.value = MainState.Failure(ex.message.toString())
                //return@launch
            }
        }
    }
}