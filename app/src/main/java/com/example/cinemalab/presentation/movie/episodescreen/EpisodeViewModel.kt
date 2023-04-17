package com.example.cinemalab.presentation.movie.episodescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.data.remote.dto.EpisodeTimeDto
import com.example.cinemalab.domain.usecase.episode.GetEpisodePositionUseCase
import com.example.cinemalab.domain.usecase.episode.PostEpisodePositionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val getEpisodePositionUseCase: GetEpisodePositionUseCase,
    private val postEpisodePositionUseCase: PostEpisodePositionUseCase
): ViewModel() {

    sealed class EpisodeState {
        object Initial: EpisodeState()
        object Loading: EpisodeState()
        class Failure(val errorMessage: String): EpisodeState()
        class Success(val position: Int): EpisodeState()
        object Quit: EpisodeState()
    }

    private val _state = MutableLiveData<EpisodeState>(EpisodeState.Initial)
    var state: LiveData<EpisodeState> = _state

    fun setInitialState() {
        _state.value = EpisodeState.Initial
    }

    fun getEpisodePosition(episodeId: String) {
        _state.value = EpisodeState.Loading

        viewModelScope.launch {
            try {
                val position = getEpisodePositionUseCase(episodeId)
                _state.value = EpisodeState.Success(position.timeInSeconds)
            } catch(ex: Exception) {
                _state.value = EpisodeState.Failure(ex.message.toString())
            }
        }
    }

    fun postEpisodePosition(episodeId: String, time: Int) {
        _state.value = EpisodeState.Loading

        GlobalScope.launch {
            try {
                postEpisodePositionUseCase(episodeId, EpisodeTimeDto(time))
            } catch(ex: Exception) {
                _state.value = EpisodeState.Failure(ex.message.toString())
            }
        }
    }
}