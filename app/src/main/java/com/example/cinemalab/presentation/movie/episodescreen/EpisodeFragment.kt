package com.example.cinemalab.presentation.movie.episodescreen

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cinemalab.R
import com.example.cinemalab.databinding.FragmentEpisodeBinding
import com.example.cinemalab.domain.model.ShortChatInfoModel
import com.example.cinemalab.domain.model.toShortChatInfoModel
import dagger.hilt.android.AndroidEntryPoint

@UnstableApi @AndroidEntryPoint
class EpisodeFragment : Fragment() {

    private lateinit var binding: FragmentEpisodeBinding
    private val viewModel: EpisodeViewModel by viewModels()

    private val args: EpisodeFragmentArgs by navArgs()

    private lateinit var videoView: PlayerView
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_episode, container, false)
        binding = FragmentEpisodeBinding.bind(view)

        setOnClickListeners()
        viewModel.setInitialState()

        val stateObserver = Observer<EpisodeViewModel.EpisodeState> { newState ->
            when(newState){
                EpisodeViewModel.EpisodeState.Initial -> {
                    hideLoading()
                    viewModel.getEpisodePosition(args.episodeInfo.episodeId)
                }
                EpisodeViewModel.EpisodeState.Loading -> {
                    showLoading()
                }
                is EpisodeViewModel.EpisodeState.Failure -> {
                    hideLoading()
                    showErrorDialog(newState.errorMessage)
                }
                is EpisodeViewModel.EpisodeState.Success -> {
                    setupEpisodeInfo()
                    setupMovieInfo()
                    setupVideoView(newState.position)

                    hideLoading()
                }
                EpisodeViewModel.EpisodeState.Quit -> {
                    findNavController().popBackStack()
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    override fun onPause() {
        //binding.grPreview.isGone = false
        exoPlayer.pause()
        viewModel.postEpisodePosition(args.episodeInfo.episodeId, (exoPlayer.currentPosition/1000).toInt())

        super.onPause()
    }

    private fun showLoading() {
        /*binding.vPreviewBackground.visibility = View.INVISIBLE
        binding.ivPreview.visibility = View.INVISIBLE
        binding.ivStartPlay.visibility = View.INVISIBLE
        binding.episodeFrame.visibility = View.INVISIBLE*/
        binding.grContent.isGone = true
        binding.loading.show()
    }

    private fun hideLoading() {
        /*binding.vPreviewBackground.visibility = View.VISIBLE
        binding.ivPreview.visibility = View.VISIBLE
        binding.ivStartPlay.visibility = View.VISIBLE
        binding.episodeFrame.visibility = View.VISIBLE*/
        binding.grContent.isGone = false
        binding.loading.hide()
    }

    private fun showErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)

        builder.setTitle(getString(R.string.input_incorrect))
        builder.setMessage(message)
        builder.show()
    }

    private fun setupEpisodeInfo() {
        binding.tvEpisodeName.text = args.episodeInfo.name
        binding.tvEpisodeDescription.text = args.episodeInfo.description

        //val textureView = binding.episodeVideoView.videoSurfaceView as TextureView
        Glide.with(this).load(args.episodeInfo.preview).into(binding.ivPreview)
    }

    private fun setupMovieInfo() {
        binding.tvMovieName.text = args.movieInfo.name
        binding.tvSeasons.text = args.episodeInfo.director
        binding.tvYear.text = args.episodeInfo.year
        Glide.with(this).load(args.movieInfo.poster).into(binding.ivMoviePoster)
    }

    private fun setupVideoView(position: Int) {
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        videoView = binding.episodeVideoView
        videoView.player = exoPlayer

        val mediaItem = MediaItem.fromUri(Uri.parse(args.episodeInfo.filePath))
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.seekTo((position*1000).toLong())
    }

    private fun setOnClickListeners() {
        setOnBackButtonClickListener()
        setOnStartPlayButtonClickListener()
        setOnChatButtonClickListener()
        setOnPlusButtonClickListener()
        setOnHeartClickListener()
    }

    private fun setOnBackButtonClickListener() {
        binding.btBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setOnStartPlayButtonClickListener() {
        binding.ivPreview.setOnClickListener {
            exoPlayer.playWhenReady = true

            binding.vPreviewBackground.visibility = View.INVISIBLE
            binding.ivPreview.visibility = View.INVISIBLE
            binding.ivStartPlay.visibility = View.INVISIBLE
        }
        /*binding.btStartPlay.setOnClickListener {
            //binding.grPreview.visibility = View.INVISIBLE
            exoPlayer.play()
        }*/
    }

    private fun setOnChatButtonClickListener() {
        binding.btChats.setOnClickListener {
            navigateToChatsActivity()
        }
    }

    private fun setOnPlusButtonClickListener() {
        binding.btPlus.setOnClickListener {

        }
    }

    private fun setOnHeartClickListener() {
        binding.btLike.setOnClickListener {

        }
    }

    private fun navigateToChatsActivity() {
        val emptyInfo = ShortChatInfoModel("Undefined", "Undefined")
        val action = EpisodeFragmentDirections.actionEpisodeFragmentToMessengerFragment2(args.movieInfo.chatInfo?.toShortChatInfoModel()
            ?: emptyInfo)
        findNavController().navigate(action)
    }

}