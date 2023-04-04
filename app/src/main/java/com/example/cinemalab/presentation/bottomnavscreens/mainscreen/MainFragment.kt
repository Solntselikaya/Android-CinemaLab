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
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.example.cinemalab.R
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
                MainViewModel.MainState.Initial -> hideLoading()
                MainViewModel.MainState.Loading -> showLoading()
                is MainViewModel.MainState.Failure ->
                    showErrorDialog(newState.errorMessage)
                is MainViewModel.MainState.Success -> {
                    hideLoading()

                    Glide.with(this)
                        .load(newState.promotedCover.cover)
                        .into(binding.ivPromotedPoster)

                    setupRecyclerViews(
                        newState.moviesTrend,
                        newState.moviesNew,
                        newState.moviesForMe
                    )

                    setupLastWatched(newState.lastWatchedMovie)
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    override fun onStart() {
        viewModel.getAllMovies()
        super.onStart()
    }

    private fun showLoading() {
        binding.grMainContent.isGone = true
        binding.loading.show()
    }

    private fun hideLoading() {
        binding.grMainContent.isGone = false
        binding.loading.hide()
    }

    private fun showErrorDialog(message: String) {
        hideLoading()
        val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)

        builder.setTitle(getString(R.string.input_incorrect))
        builder.setMessage(message)
        builder.show()
    }

    private fun setupRecyclerViews(
        moviesTrend: List<MovieModel>,
        moviesNew: List<MovieModel>,
        moviesForYou: List<MovieModel>
    ) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        if (moviesTrend.isNotEmpty()) {
            setupMoviesTrendRecyclerView(
                layoutManager,
                moviesTrend
            )
        }
        else {
            binding.rvTrends.isGone = true
            binding.tvMoviesTrend.isGone = true
        }

        if (moviesNew.isNotEmpty()) {
            setupMoviesNewRecyclerView(
                layoutManager,
                moviesNew
            )
        }
        else {
            binding.rvNewMovies.isGone = true
            binding.tvMoviesNew.isGone = true
        }

        if (moviesForYou.isNotEmpty()) {
            setupMoviesForYouRecyclerView(
                layoutManager,
                moviesForYou
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
        binding.rvMoviesForYou.layoutManager = layoutManager
        binding.rvMoviesForYou.adapter = CustomRecyclerAdapter(movies, this)
    }

    private fun setupLastWatched(movies: List<MovieModel>) {
        if (movies.isNotEmpty() && movies.last().poster.isNotBlank()) {
            binding.cvLastWatched.isGone = false
            Glide.with(this).load(movies.last().poster).into(binding.ivLastWatchedPoster)
            binding.tvLastWatched.text = movies.last().name
        }
        else {
            binding.cvLastWatched.isGone = true
            binding.tvMoviesViewed.isGone = true
        }
    }

}