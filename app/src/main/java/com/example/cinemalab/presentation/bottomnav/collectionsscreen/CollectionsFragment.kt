package com.example.cinemalab.presentation.bottomnav.collectionsscreen

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
import com.example.cinemalab.R
import com.example.cinemalab.databinding.FragmentCollectionsBinding
import com.example.cinemalab.domain.model.CollectionModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionsFragment : Fragment() {

    private lateinit var binding: FragmentCollectionsBinding
    private val viewModel: CollectionsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_collections, container, false)
        binding = FragmentCollectionsBinding.bind(view)

        setOnClickListeners()

        val stateObserver = Observer<CollectionsViewModel.CollectionsState> { newState ->
            when(newState) {
                CollectionsViewModel.CollectionsState.Initial -> {
                    hideLoading()
                }
                CollectionsViewModel.CollectionsState.Loading -> {
                    showLoading()
                }
                is CollectionsViewModel.CollectionsState.Failure -> {
                    hideLoading()
                    showErrorDialog(newState.errorMessage)
                }
                is CollectionsViewModel.CollectionsState.Success -> {
                    hideLoading()
                    setupRecyclerView(newState.collections)
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    override fun onStart() {
        viewModel.getCollections()
        super.onStart()
    }

    private fun setOnClickListeners() {
        setOnPlusButtonClickListener()
    }

    private fun showLoading() {
        binding.loading.show()
        binding.grCollectionsContent.isGone = true
    }

    private fun hideLoading() {
        binding.loading.hide()
        binding.grCollectionsContent.isGone = false
    }

    private fun showErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)

        builder.setTitle(getString(R.string.input_incorrect))
        builder.setMessage(message)
        builder.show()
    }

    private fun setOnPlusButtonClickListener() {
        binding.btPlus.setOnClickListener {
            navigateToCreateCollectionScreen()
        }
    }

    private fun setupRecyclerView(collections: List<CollectionModel>) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.rvCollections.layoutManager = layoutManager
        binding.rvCollections.adapter = CollectionsRecyclerAdapter(collections) { navigateToCollectionDetails(it) }
    }

    private fun navigateToCreateCollectionScreen() {
        findNavController().navigate(R.id.action_collectionsFragment_to_collectionCreateActivity)
    }

    private fun navigateToCollectionDetails(collectionInfo: CollectionModel) {
        val action = CollectionsFragmentDirections.actionCollectionsFragmentToCollectionActionsActivity(collectionInfo)
        findNavController().navigate(action)
    }

}