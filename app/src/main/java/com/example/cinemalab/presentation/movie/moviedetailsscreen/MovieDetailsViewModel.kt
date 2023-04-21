package com.example.cinemalab.presentation.movie.moviedetailsscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.data.remote.dto.toEpisodeModel
import com.example.cinemalab.domain.model.EpisodeModel
import com.example.cinemalab.domain.usecase.movie.GetMovieEpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieEpisodesUseCase: GetMovieEpisodesUseCase
) : ViewModel() {

    sealed class MovieDetailsState {
        object Initial : MovieDetailsState()
        object Loading : MovieDetailsState()
        class Failure(val errorMessage: String) : MovieDetailsState()
        class Success(
            val episodes: List<EpisodeModel>
        ) : MovieDetailsState()

        class Navigate(
            val episodeInfo: EpisodeModel
        ) : MovieDetailsState()
    }

    private val _state = MutableLiveData<MovieDetailsState>(MovieDetailsState.Initial)
    var state: LiveData<MovieDetailsState> = _state

    fun getMovieEpisodes(
        movieId: String,
        episodeId: String?
    ) {
        _state.value = MovieDetailsState.Loading

        viewModelScope.launch {
            try {
                val episodes = getMovieEpisodesUseCase(movieId)

                if (episodeId != null) {
                    val episodeInfo = episodes.find { it.episodeId == episodeId }
                    _state.value = MovieDetailsState.Navigate(episodeInfo!!.toEpisodeModel())
                } else {
                    _state.value = MovieDetailsState.Success(episodes.map { it.toEpisodeModel() })
                }
            } catch (ex: Exception) {
                _state.value = MovieDetailsState.Failure(ex.message.toString())
            }
        }
    }
}