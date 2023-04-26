package com.example.cinemalab.presentation.movie.moviedetailsscreen

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cinemalab.R
import com.example.cinemalab.databinding.FragmentMovieDetailsBinding
import com.example.cinemalab.domain.model.*
import com.example.cinemalab.presentation.movie.MovieActivity
import com.example.cinemalab.presentation.movie.moviedetailsscreen.recycleradapters.MovieEpisodesRecyclerAdapter
import com.example.cinemalab.presentation.movie.moviedetailsscreen.recycleradapters.MovieFramesRecyclerAdapter
import com.example.cinemalab.presentation.movie.moviedetailsscreen.recycleradapters.MovieTagsRecyclerAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var callback: MovieDetailsListener? = null

    interface MovieDetailsListener {
        fun onMovieDetailsBackPressed()
    }

    //фрагмент глупый и простой
    //так что все переменные (тк состояние экрана) должны быть в VM
    private var movieInfo: MovieModel? = null
    private var episodeId: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as MovieDetailsListener

        val activity: MovieActivity? = activity as MovieActivity?
        movieInfo = activity?.getMovieModel()
        episodeId = activity?.getEpisodeId()
    }

    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)
        binding = FragmentMovieDetailsBinding.bind(view)

        setOnClickListeners()

        val stateObserver = Observer<MovieDetailsViewModel.MovieDetailsState> { newState ->
            when (newState) {
                MovieDetailsViewModel.MovieDetailsState.Initial -> {
                    hideLoading()
                    viewModel.getMovieEpisodes(
                        movieInfo?.movieId ?: "",
                        episodeId
                    )
                }
                MovieDetailsViewModel.MovieDetailsState.Loading -> {
                    showLoading()
                }
                is MovieDetailsViewModel.MovieDetailsState.Failure -> {
                    hideLoading()
                    showErrorDialog(newState.errorMessage)
                }
                is MovieDetailsViewModel.MovieDetailsState.Success -> {
                    hideLoading()

                    setupPoster()
                    setupDescription()
                    setupAgeLimit()

                    setupRecyclerViews(
                        movieInfo?.tags ?: emptyList(),
                        movieInfo?.imageUrls ?: emptyList(),
                        newState.episodes
                    )

                    if (newState.episodes.size > 1) {
                        binding.btGoWatch.setOnClickListener {
                            navigateToEpisodeScreen(newState.episodes.first())
                        }
                    }
                }
                is MovieDetailsViewModel.MovieDetailsState.Navigate -> {
                    if (episodeId == null) {
                        viewModel.getMovieEpisodes(
                            movieInfo?.movieId ?: "",
                            episodeId
                        )
                    } else {
                        episodeId = null
                        navigateToEpisodeScreen(newState.episodeInfo)
                    }
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    private fun showLoading() {
        binding.grMovieDetailsContent.isGone = true
        binding.loading.show()
    }

    private fun hideLoading() {
        binding.grMovieDetailsContent.isGone = false
        binding.loading.hide()
    }

    private fun showErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)

        builder.setTitle(getString(R.string.input_incorrect))
        builder.setMessage(message)
        builder.show()
    }

    private fun setOnClickListeners() {
        setOnBackButtonClickListener()
        setOnChatsButtonClickListener()
    }

    private fun setOnBackButtonClickListener() {
        binding.btBack.setOnClickListener {
            callback?.onMovieDetailsBackPressed()
        }
    }

    private fun setOnChatsButtonClickListener() {
        binding.btChats.setOnClickListener {
            navigateToMessengerScreen()
        }
    }

    private fun setupPoster() {
        Glide.with(this).load(movieInfo?.poster).into(binding.ivPoster)
    }

    private fun setupDescription() {
        binding.tvDescription.text = movieInfo?.description
    }

    private fun setupAgeLimit() {
        binding.tvAgeLimit.text = getString(movieInfo?.age?.value ?: R.string.age_limit_0)
        val color = getColor(requireActivity(), movieInfo?.age?.color ?: R.color.age_limit_0)
        binding.tvAgeLimit.setTextColor(color)
    }

    private fun setupRecyclerViews(
        tags: List<TagModel>,
        frames: List<String>,
        episodes: List<EpisodeModel>
    ) {
        if (tags.isNotEmpty()) {
            setupTagsRecyclerView(tags)
        } else {
            binding.rvTags.isGone = true
        }

        if (frames.isNotEmpty()) {
            setupFragmentsRecyclerView(frames)
        } else {
            binding.tvFramesHeader.isGone = true
            binding.rvFrames.isGone = true
        }

        if (episodes.isNotEmpty()) {
            setupEpisodesRecyclerView(episodes)
        } else {
            binding.tvEpisodesHeader.isGone = true
            binding.rvEpisodes.isGone = true
        }
    }

    private fun setupTagsRecyclerView(tags: List<TagModel>) {
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.justifyContent = JustifyContent.FLEX_START

        binding.rvTags.layoutManager = layoutManager
        binding.rvTags.adapter = MovieTagsRecyclerAdapter(tags)
    }

    private fun setupFragmentsRecyclerView(frames: List<String>) {
        binding.rvFrames.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.rvFrames.adapter = MovieFramesRecyclerAdapter(frames)
    }

    private fun setupEpisodesRecyclerView(episodes: List<EpisodeModel>) {
        binding.rvEpisodes.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.rvEpisodes.adapter =
            MovieEpisodesRecyclerAdapter(episodes) { navigateToEpisodeScreen(it) }
    }

    private fun navigateToEpisodeScreen(episodeInfo: EpisodeModel) {
        val movieModel = movieInfo?.toMovieShortModel() ?: MovieShortModel("", "", "", null)
        val action = MovieDetailsFragmentDirections.actionMovieDetailsFragmentToEpisodeFragment(
            movieModel,
            episodeInfo
        )
        findNavController().navigate(action)
    }

    private fun navigateToMessengerScreen() {
        val emptyInfo = ShortChatInfoModel("Undefined", "Undefined")
        val action = MovieDetailsFragmentDirections.actionMovieDetailsFragmentToMessengerFragment2(
            movieInfo?.chatInfo?.toShortChatInfoModel()
                ?: emptyInfo
        )
        findNavController().navigate(action)
    }

}