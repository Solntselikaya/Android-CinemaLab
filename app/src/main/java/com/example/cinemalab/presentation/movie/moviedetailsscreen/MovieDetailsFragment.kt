package com.example.cinemalab.presentation.movie.moviedetailsscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.cinemalab.R
import com.example.cinemalab.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)
        binding = FragmentMovieDetailsBinding.bind(view)

        val stateObserver = Observer<MovieDetailsViewModel.MovieDetailsState> { newState ->
            when(newState) {
                MovieDetailsViewModel.MovieDetailsState.Initial -> {

                }
                MovieDetailsViewModel.MovieDetailsState.Loading -> {

                }
                is MovieDetailsViewModel.MovieDetailsState.Failure -> {

                }
                MovieDetailsViewModel.MovieDetailsState.Success -> {

                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    private fun showLoading() {

    }

    private fun hideLoading() {

    }

    private fun showErrorDialog() {

    }

}