package com.example.cinemalab.presentation.bottomnav.compilationscreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.data.remote.dto.MovieIdDto
import com.example.cinemalab.data.remote.dto.toMovieModel
import com.example.cinemalab.domain.model.Filters
import com.example.cinemalab.domain.model.MovieModel
import com.example.cinemalab.domain.usecase.collection.api.AddMovieToCollectionUseCase
import com.example.cinemalab.domain.usecase.collection.storage.GetFavouritesCollectionIdUseCase
import com.example.cinemalab.domain.usecase.movie.DislikeMovieInCompilationUseCase
import com.example.cinemalab.domain.usecase.movie.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompilationViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val dislikeMovieUseCase: DislikeMovieInCompilationUseCase,
    private val addMovieToCollectionUseCase: AddMovieToCollectionUseCase,
    private val getFavouritesCollectionIdUseCase: GetFavouritesCollectionIdUseCase
) : ViewModel() {

    sealed class CompilationState {
        object Initial : CompilationState()
        object Loading : CompilationState()
        class Failure(val errorMessage: String) : CompilationState()
        class Success(val compilation: List<MovieModel>) : CompilationState()

    }

    private val _state = MutableLiveData<CompilationState>(CompilationState.Initial)
    var state: LiveData<CompilationState> = _state

    private val _currMovieId = MutableLiveData("")
    var currMovieId: LiveData<String> = _currMovieId

    fun setNewCurrMovieId(newId: String) {
        _currMovieId.value = newId
    }

    /*init {
        getCompilation()
    }*/

    fun getCompilation() {

        _state.value = CompilationState.Loading

        viewModelScope.launch {
            try {
                val compilation = getMoviesUseCase(Filters.compilation.name)
                _state.value = CompilationState.Success(
                    compilation.map { it.toMovieModel() }
                )
            } catch (ex: Exception) {
                _state.value = CompilationState.Failure(ex.message.toString())
            }
        }
    }

    fun dislikeCurrMovie() {
        viewModelScope.launch {
            try {
                dislikeMovieUseCase(_currMovieId.value.toString())
            } catch (ex: Exception) {
                _state.value = CompilationState.Failure(ex.message.toString())
            }
        }
    }

    fun addCurrMovieToFavorites() {
        viewModelScope.launch {
            try {
                val favId = getFavouritesCollectionIdUseCase()
                addMovieToCollectionUseCase(favId, MovieIdDto(_currMovieId.value.toString()))

            } catch (ex: Exception) {
                _state.value = CompilationState.Failure(ex.message.toString())
            }
        }
    }

}
