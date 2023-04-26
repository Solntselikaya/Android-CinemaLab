package com.example.cinemalab.presentation.movie.episodescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.data.db.entities.toCollectionModel
import com.example.cinemalab.data.remote.dto.EpisodeTimeDto
import com.example.cinemalab.data.remote.dto.MovieIdDto
import com.example.cinemalab.domain.model.CollectionModel
import com.example.cinemalab.domain.usecase.collection.api.AddMovieToCollectionUseCase
import com.example.cinemalab.domain.usecase.collection.api.DeleteMovieFromCollectionUseCase
import com.example.cinemalab.domain.usecase.collection.api.GetCollectionMoviesUseCase
import com.example.cinemalab.domain.usecase.collection.db.GetUserCollectionsFromDatabaseUseCase
import com.example.cinemalab.domain.usecase.collection.storage.GetFavouritesCollectionIdUseCase
import com.example.cinemalab.domain.usecase.episode.GetEpisodePositionUseCase
import com.example.cinemalab.domain.usecase.episode.PostEpisodePositionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val getEpisodePositionUseCase: GetEpisodePositionUseCase,
    private val postEpisodePositionUseCase: PostEpisodePositionUseCase,
    private val getUserCollectionsFromDatabaseUseCase: GetUserCollectionsFromDatabaseUseCase,
    private val addMovieToCollectionUseCase: AddMovieToCollectionUseCase,
    private val getFavouritesCollectionIdUseCase: GetFavouritesCollectionIdUseCase,
    private val deleteMovieFromCollectionUseCase: DeleteMovieFromCollectionUseCase,
    private val getCollectionMoviesUseCase: GetCollectionMoviesUseCase
) : ViewModel() {

    sealed class EpisodeState {
        object Initial : EpisodeState()
        object Loading : EpisodeState()
        class Failure(val errorMessage: String) : EpisodeState()
        class Success(
            val position: Int,
            val collections: List<CollectionModel>
        ) : EpisodeState()

        object Quit : EpisodeState()
    }

    private val _state = MutableLiveData<EpisodeState>(EpisodeState.Initial)
    var state: LiveData<EpisodeState> = _state

    private val _isInFavourites = MutableLiveData<Boolean>()
    var isInFavourites: LiveData<Boolean> = _isInFavourites

    fun setInitialState() {
        _state.value = EpisodeState.Initial
    }

    fun initEpisodeScreen(
        movieId: String,
        episodeId: String
    ) {
        _state.value = EpisodeState.Loading

        //тут поток не надо менять
        //юзкейс!
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val collections = getUserCollectionsFromDatabaseUseCase()
                val position = getEpisodePositionUseCase(episodeId)
                checkIfMovieIsInFavourites(movieId)

                while (_isInFavourites.value == null) {
                }

                _state.postValue(
                    EpisodeState.Success(
                        position.timeInSeconds,
                        collections.map { it.toCollectionModel() }
                    )
                )
            } catch (ex: Exception) {
                _state.value = EpisodeState.Failure(ex.message.toString())
            }
        }
    }

    private suspend fun checkIfMovieIsInFavourites(movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val favId = getFavouritesCollectionIdUseCase()
            val favouriteMovies = getCollectionMoviesUseCase(favId)

            for (movie in favouriteMovies) {
                if (movie.movieId == movieId) {
                    _isInFavourites.postValue(true)
                    break
                } else {
                    _isInFavourites.postValue(false)
                }
            }
        }
    }

    /*private suspend fun getEpisodePosition(episodeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val position = getEpisodePositionUseCase(episodeId)
                //val collections = getUserCollectionsFromDatabaseUseCase()

                *//*_state.postValue(
                    EpisodeState.Success(
                        position.timeInSeconds,
                        collections.map { it.toCollectionModel() }
                    )
                )*//*
            } catch(ex: Exception) {
                _state.value = EpisodeState.Failure(ex.message.toString())
            }
        }
    }*/

    fun postEpisodePosition(episodeId: String, time: Int) {
        _state.value = EpisodeState.Loading

        GlobalScope.launch {
            try {
                postEpisodePositionUseCase(episodeId, EpisodeTimeDto(time))
            } catch (ex: Exception) {
                _state.value = EpisodeState.Failure(ex.message.toString())
            }
        }
    }

    fun onMenuItemClickListener(
        collectionId: String,
        movieId: String
    ): Boolean {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movie = MovieIdDto(movieId)
                addMovieToCollectionUseCase(collectionId, movie)
            } catch (ex: Exception) {
                _state.postValue(
                    EpisodeState.Failure(ex.message.toString())
                )
            }
        }
        return true
    }

    fun addMovieToFavourites(movieId: String) {
        val movie = MovieIdDto(movieId)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val favId = getFavouritesCollectionIdUseCase()
                addMovieToCollectionUseCase(favId, movie)
                _isInFavourites.postValue(true)
            } catch (ex: Exception) {
                _state.postValue(
                    EpisodeState.Failure(ex.message.toString())
                )
            }
        }
    }

    fun deleteMovieFromFavourites(movieId: String) {
        val favId = getFavouritesCollectionIdUseCase()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteMovieFromCollectionUseCase(favId, movieId)
                _isInFavourites.postValue(false)
            } catch (ex: Exception) {
                _state.postValue(
                    EpisodeState.Failure(ex.message.toString())
                )
            }
        }
    }
}