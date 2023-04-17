package com.example.cinemalab.presentation.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.navArgs
import com.example.cinemalab.R
import com.example.cinemalab.domain.model.MovieModel
import com.example.cinemalab.presentation.chats.messenger.MessengerFragment
import com.example.cinemalab.presentation.movie.moviedetailsscreen.MovieDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity :
    AppCompatActivity(),
    MovieDetailsFragment.MovieDetailsListener,
    MessengerFragment.MessengerListener
{

    private val args: MovieActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CinemaLab)
        setContentView(R.layout.activity_movie)
    }

    fun getMovieModel(): MovieModel {
        return args.movieInfo
    }

    override fun onMovieDetailsBackPressed() {
        finish()
    }

    override fun onMessengerExit() {
        finish()
    }
}