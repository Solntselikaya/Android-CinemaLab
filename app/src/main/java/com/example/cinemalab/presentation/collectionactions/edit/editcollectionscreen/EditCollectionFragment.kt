package com.example.cinemalab.presentation.collectionactions.edit.editcollectionscreen

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.cinemalab.R
import com.example.cinemalab.databinding.FragmentEditCollectionBinding
import com.example.cinemalab.domain.model.CollectionModel
import com.example.cinemalab.presentation.collectionactions.edit.CollectionEditActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCollectionFragment : Fragment() {

    private var callback: CollectionEditListener? = null
    private var collectionInfo: CollectionModel? = null

    interface CollectionEditListener {

        fun onCollectionNameEdit(newName: String)
        fun onCollectionDelete()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as CollectionEditListener

        val activity: CollectionEditActivity? = activity as CollectionEditActivity?
        collectionInfo = activity?.getCurrCollection()
    }

    private lateinit var binding: FragmentEditCollectionBinding
    private val viewModel: EditCollectionViewModel by viewModels()

    override fun onStart() {
        setupIcon()
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_edit_collection, container, false)
        binding = FragmentEditCollectionBinding.bind(view)

        setOnClickListeners()

        val stateObserver = Observer<EditCollectionViewModel.EditCollectionState> { newState ->
            when (newState) {
                EditCollectionViewModel.EditCollectionState.Initial -> {
                    binding.etCollectionName.setText(collectionInfo?.name)
                }
                EditCollectionViewModel.EditCollectionState.Loading -> {
                    showLoading()
                }
                is EditCollectionViewModel.EditCollectionState.Failure -> {
                    hideLoading()
                    showErrorDialog(newState.errorMessage)
                }
                EditCollectionViewModel.EditCollectionState.Success -> {
                    navigateToCollectionDetails()
                }
                EditCollectionViewModel.EditCollectionState.Quit -> {
                    callback?.onCollectionDelete()
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    private fun setupIcon() {
        val activity: CollectionEditActivity? = activity as CollectionEditActivity?
        val collectionInfo = activity?.getCurrCollection()

        binding.ivIcon.setImageResource(collectionInfo?.icon ?: R.drawable.collection_icon_01)
    }

    private fun showLoading() {
        //binding.grContent.isGone = true
        binding.loading.show()
    }

    private fun hideLoading() {
        //binding.grContent.isGone = false
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
        setOnSelectIconClickListener()
        setOnSaveButtonClickListener()
        setOnDeleteButtonClickListener()
    }

    private fun setOnBackButtonClickListener() {
        binding.btBack.setOnClickListener {
            navigateToCollectionDetails()
        }
    }

    private fun setOnSelectIconClickListener() {
        binding.btChooseIcon.setOnClickListener {
            saveEditedName()
            navigateToIconSelectScreen()
        }
    }

    private fun setOnSaveButtonClickListener() {
        binding.btSave.setOnClickListener {
            saveEditedName()
            val activity: CollectionEditActivity? = activity as CollectionEditActivity?
            collectionInfo = activity?.getCurrCollection()
            viewModel.saveEditedCollection(collectionInfo)
        }
    }

    private fun setOnDeleteButtonClickListener() {
        binding.btDelete.setOnClickListener {
            viewModel.deleteCollection(collectionInfo?.id ?: "")
        }
    }

    private fun saveEditedName() {
        callback?.onCollectionNameEdit(
            binding.etCollectionName.text.toString()
        )
    }

    private fun navigateToCollectionDetails() {
        findNavController().navigate(R.id.action_editCollectionFragment_to_collectionDetailsFragment)
    }

    private fun navigateToIconSelectScreen() {
        findNavController().navigate(R.id.action_editCollectionFragment_to_iconSelectFragment)
    }

}