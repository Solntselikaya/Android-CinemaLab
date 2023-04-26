package com.example.cinemalab.presentation.bottomnav.mainscreen

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.example.cinemalab.R
import com.example.cinemalab.databinding.FragmentMainBinding
import com.example.cinemalab.domain.model.HistoryModel
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

        //val stateObserver = Observer<MainViewModel.MainState> {}
        //вынести метод отсюда в отдельный чтобы не засорять метод жизненного цикла
        viewModel.state.observe(viewLifecycleOwner) { newState ->
            when (newState) {
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

                    setupLastWatched(
                        newState.lastWatchedMovie,
                        newState.lastWatchedEpisodes
                    )
                }
            }
        }

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

        if (moviesTrend.isNotEmpty()) {
            setupMoviesTrendRecyclerView(
                moviesTrend
            )
        } else {
            binding.rvTrends.isGone = true
            binding.tvMoviesTrend.isGone = true
        }

        if (moviesNew.isNotEmpty()) {
            setupMoviesNewRecyclerView(
                moviesNew
            )
        } else {
            binding.rvNewMovies.isGone = true
            binding.tvMoviesNew.isGone = true
        }

        if (moviesForYou.isNotEmpty()) {
            setupMoviesForYouRecyclerView(
                moviesForYou
            )
        } else {
            binding.rvMoviesForYou.isGone = true
            binding.tvMoviesForYou.isGone = true
        }
    }

    private fun setupMoviesTrendRecyclerView(
        movies: List<MovieModel>
    ) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.rvTrends.layoutManager = layoutManager
        binding.rvTrends.adapter = CustomRecyclerAdapter(
            R.layout.item_movie_cover_small,
            movies,
            this
        ) { openMovieDetails(it) }
    }

    private fun setupMoviesNewRecyclerView(
        movies: List<MovieModel>
    ) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.rvNewMovies.layoutManager = layoutManager
        binding.rvNewMovies.adapter = CustomRecyclerAdapter(
            R.layout.item_movie_cover_large,
            movies,
            this
        ) { openMovieDetails(it) }
    }

    private fun setupMoviesForYouRecyclerView(
        movies: List<MovieModel>
    ) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.rvMoviesForYou.layoutManager = layoutManager
        binding.rvMoviesForYou.adapter = CustomRecyclerAdapter(
            R.layout.item_movie_cover_small,
            movies,
            this
        ) { openMovieDetails(it) }
    }

    private fun setupLastWatched(
        movies: List<MovieModel>,
        episodes: List<HistoryModel>
    ) {
        if (movies.isNotEmpty() && movies.last().poster.isNotBlank()) {
            binding.cvLastWatched.isGone = false
            Glide.with(this).load(movies.last().poster).into(binding.ivLastWatchedPoster)
            binding.tvLastWatched.text = movies.last().name

            val lastEpisode = episodes.first()
            val movieModel = movies.find { it.movieId == lastEpisode.movieId }

            binding.cvLastWatched.setOnClickListener {
                if (movieModel != null) {
                    openEpisode(movieModel, lastEpisode.episodeId)
                }
            }
        } else {
            binding.cvLastWatched.isGone = true
            binding.tvMoviesViewed.isGone = true
        }
    }

    private fun openMovieDetails(movie: MovieModel) {
        val action = MainFragmentDirections.actionMainFragmentToMovieActivity(movie)
        findNavController().navigate(action)
    }

    private fun openEpisode(movie: MovieModel, episodeId: String) {
        val action = MainFragmentDirections.actionMainFragmentToMovieActivity(movie, episodeId)
        findNavController().navigate(action)
    }

}