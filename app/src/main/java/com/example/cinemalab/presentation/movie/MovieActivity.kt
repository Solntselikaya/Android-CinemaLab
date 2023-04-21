package com.example.cinemalab.presentation.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.example.cinemalab.R
import com.example.cinemalab.domain.model.MovieModel
import com.example.cinemalab.presentation.chats.chat.ChatFragment
import com.example.cinemalab.presentation.movie.moviedetailsscreen.MovieDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity :
    AppCompatActivity(),
    MovieDetailsFragment.MovieDetailsListener,
    ChatFragment.MessengerListener {

    private val args: MovieActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CinemaLab)
        setContentView(R.layout.activity_movie)
    }

    fun getMovieModel(): MovieModel {
        return args.movieInfo
    }

    fun getEpisodeId(): String? {
        return args.episodeId
    }

    override fun onMovieDetailsBackPressed() {
        finish()
    }

    override fun onMessengerExit() {
        finish()
    }
}