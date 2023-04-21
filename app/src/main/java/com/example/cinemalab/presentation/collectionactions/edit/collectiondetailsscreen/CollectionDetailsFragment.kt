package com.example.cinemalab.presentation.collectionactions.edit.collectiondetailsscreen

import android.app.AlertDialog
import android.content.Context
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
import com.example.cinemalab.R
import com.example.cinemalab.databinding.FragmentCollectionDetailsBinding
import com.example.cinemalab.domain.model.CollectionModel
import com.example.cinemalab.domain.model.MovieModel
import com.example.cinemalab.presentation.collectionactions.edit.CollectionEditActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionDetailsFragment : Fragment() {

    private var callback: CollectionDetailsListener? = null

    interface CollectionDetailsListener {
        fun onDetailsBackPressed()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as CollectionDetailsListener
    }

    private lateinit var binding: FragmentCollectionDetailsBinding
    private val viewModel: CollectionDetailsViewModel by viewModels()

    private var collectionInfo: CollectionModel? = null

    override fun onStart() {
        setupName()
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_collection_details, container, false)
        binding = FragmentCollectionDetailsBinding.bind(view)

        val activity: CollectionEditActivity? = activity as CollectionEditActivity?
        collectionInfo = activity?.getCurrCollection()
        setOnClickListeners()

        val stateObserver =
            Observer<CollectionDetailsViewModel.CollectionDetailsState> { newState ->
                when (newState) {
                    CollectionDetailsViewModel.CollectionDetailsState.Initial -> {
                        binding.tvCollectionName.text = collectionInfo?.name ?: ""
                        viewModel.getCollectionMovies(collectionInfo?.id ?: "")
                    }
                    CollectionDetailsViewModel.CollectionDetailsState.Loading -> {
                        showLoading()
                    }
                    is CollectionDetailsViewModel.CollectionDetailsState.Failure -> {
                        hideLoading()
                        showErrorDialog(newState.errorMessage)
                    }
                    is CollectionDetailsViewModel.CollectionDetailsState.Success -> {
                        hideLoading()
                        setupRecyclerView(newState.movies)
                    }
                }
            }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    private fun setupName() {
        val activity: CollectionEditActivity? = activity as CollectionEditActivity?
        val collectionInfo = activity?.getCurrCollection()

        binding.tvCollectionName.text = collectionInfo?.name
    }

    private fun showLoading() {
        binding.rvMovies.isGone = true
        binding.loading.show()
    }

    private fun hideLoading() {
        binding.rvMovies.isGone = false
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
        setOnEditButtonClickListener()
    }

    private fun setupRecyclerView(movies: List<MovieModel>) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.rvMovies.layoutManager = layoutManager
        binding.rvMovies.adapter = CollectionMoviesRecyclerAdapter(movies)
    }

    private fun setOnBackButtonClickListener() {
        binding.btBack.setOnClickListener {
            callback?.onDetailsBackPressed()
        }
    }

    private fun setOnEditButtonClickListener() {
        if (viewModel.isCollectionFavourites(collectionInfo?.id ?: "")) {
            binding.btEdit.isGone = true
        } else {
            binding.btEdit.isGone = false
            binding.btEdit.setOnClickListener {
                navigateToEditCollectionScreen()
            }
        }
    }

    private fun navigateToEditCollectionScreen() {
        findNavController().navigate(R.id.action_collectionDetailsFragment_to_editCollectionFragment)
    }

}