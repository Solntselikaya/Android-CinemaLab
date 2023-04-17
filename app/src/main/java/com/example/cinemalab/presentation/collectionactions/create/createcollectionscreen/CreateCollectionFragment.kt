package com.example.cinemalab.presentation.collectionactions.create.createcollectionscreen

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
import com.example.cinemalab.R
import com.example.cinemalab.databinding.FragmentCreateCollectionBinding
import com.example.cinemalab.presentation.collectionactions.create.CollectionCreateActivity
import com.example.cinemalab.presentation.movie.MovieActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCollectionFragment : Fragment() {

    private var callback: CollectionCreateListener? = null
    interface CollectionCreateListener {
        fun onCollectionCreated()
        fun onCreateBackPressed()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as CollectionCreateListener
    }

    private lateinit var binding: FragmentCreateCollectionBinding
    private val viewModel: CreateCollectionViewModel by viewModels()

    override fun onStart() {
        setupIcon()
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_create_collection, container, false)
        binding = FragmentCreateCollectionBinding.bind(view)

        setOnClickListeners()

        val stateObserver = Observer<CreateCollectionViewModel.CreateCollectionState> { newState ->
            when(newState) {
                CreateCollectionViewModel.CreateCollectionState.Initial -> Unit
                CreateCollectionViewModel.CreateCollectionState.Loading -> {
                    showLoading()
                }
                is CreateCollectionViewModel.CreateCollectionState.Failure -> {
                    hideLoading()
                    showErrorDialog(newState.errorMessage)
                }
                CreateCollectionViewModel.CreateCollectionState.Success -> {
                    hideLoading()
                    callback?.onCollectionCreated()
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    private fun setupIcon() {
        val activity: CollectionCreateActivity? = activity as CollectionCreateActivity?
        val collectionInfo = activity?.getCollection()

        binding.ivIcon.setImageResource(collectionInfo?.icon ?: R.drawable.collection_icon_01)
    }

    private fun showLoading() {
        binding.grContent.isGone = true
        binding.loading.show()
    }

    private fun hideLoading() {
        binding.grContent.isGone = false
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
        setOnChooseIconButtonClickListener()
        setOnSaveButtonClickListener()
    }

    private fun setOnBackButtonClickListener() {
        binding.btBack.setOnClickListener {
            callback?.onCreateBackPressed()
        }
    }

    private fun setOnChooseIconButtonClickListener() {
        binding.btChooseIcon.setOnClickListener {
            findNavController().navigate(R.id.action_createCollectionFragment_to_iconSelectFragment2)
        }
    }

    private fun setOnSaveButtonClickListener() {
        binding.btSave.setOnClickListener {

            val activity: CollectionCreateActivity? = activity as CollectionCreateActivity?
            val collectionInfo = activity?.getCollection()

            viewModel.createCollection(
                binding.etCollectionName.text.toString(),
                collectionInfo?.icon
            )
        }
    }

}