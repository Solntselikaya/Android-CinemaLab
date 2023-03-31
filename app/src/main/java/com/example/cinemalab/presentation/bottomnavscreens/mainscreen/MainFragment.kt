package com.example.cinemalab.presentation.bottomnavscreens.mainscreen

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.annotation.GlideModule
import com.example.cinemalab.R
import com.example.cinemalab.data.remote.dto.MovieDto
import com.example.cinemalab.data.remote.dto.toMovieModel
import com.example.cinemalab.databinding.FragmentMainBinding
import com.example.cinemalab.domain.model.MovieModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@GlideModule
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        binding = FragmentMainBinding.bind(view)

        val stateObserver = Observer<MainViewModel.MainState> { newState ->
            when(newState) {
                MainViewModel.MainState.Initial -> {
                    binding.loadingScreen.isGone = true
                }
                MainViewModel.MainState.Loading ->
                    showLoading()
                is MainViewModel.MainState.Failure ->
                    showErrorDialog(newState.errorMessage)
                is MainViewModel.MainState.Success -> {
                    binding.loadingScreen.isGone = true
                    setupRecyclerViews(
                        newState.moviesTrend,
                        newState.moviesNew,
                        newState.moviesForMe
                    )
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    private fun showLoading() {
        binding.loadingScreen.isGone = false
    }

    private fun showErrorDialog(message: String) {
        binding.loadingScreen.isGone = true
        val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)

        builder.setTitle(getString(R.string.input_incorrect))
        builder.setMessage(message)
        builder.show()
    }

    private fun setupRecyclerViews(
        moviesTrend: List<MovieDto>,
        moviesNew: List<MovieDto>,
        moviesForYou: List<MovieDto>
    ) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        if (moviesTrend.isNotEmpty()) {
            setupMoviesTrendRecyclerView(
                layoutManager,
                moviesTrend.map { it.toMovieModel() }
            )
        }
        else {
            binding.rvTrends.isGone = true
            binding.tvMoviesTrend.isGone = true
        }

        if (moviesNew.isNotEmpty()) {
            setupMoviesNewRecyclerView(
                layoutManager,
                moviesNew.map { it.toMovieModel() }
            )
        }
        else {
            binding.rvNewMovies.isGone = true
            binding.tvMoviesViewed.isGone = true
        }

        if (moviesForYou.isNotEmpty()) {
            setupMoviesForYouRecyclerView(
                layoutManager,
                moviesForYou.map { it.toMovieModel() }
            )
        }
        else {
            binding.rvMoviesForYou.isGone = true
            binding.tvMoviesForYou.isGone = true
        }
    }

    private fun setupMoviesTrendRecyclerView(
        layoutManager: LayoutManager,
        movies: List<MovieModel>
    ) {
        binding.rvTrends.layoutManager = layoutManager
        binding.rvTrends.adapter = CustomRecyclerAdapter(movies, this)
    }

    private fun setupMoviesNewRecyclerView(
        layoutManager: LayoutManager,
        movies: List<MovieModel>
    ) {
        binding.rvNewMovies.layoutManager = layoutManager
        binding.rvNewMovies.adapter = CustomRecyclerAdapter(movies, this)
    }

    private fun setupMoviesForYouRecyclerView(
        layoutManager: LayoutManager,
        movies: List<MovieModel>
    ) {
        binding.rvTrends.layoutManager = layoutManager
        binding.rvMoviesForYou.adapter = CustomRecyclerAdapter(movies, this)
    }

}