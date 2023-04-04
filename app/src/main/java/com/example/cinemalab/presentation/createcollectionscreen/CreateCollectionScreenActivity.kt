package com.example.cinemalab.presentation.createcollectionscreen

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.Observer
import com.example.cinemalab.R
import com.example.cinemalab.databinding.ActivityCreateCollectionScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCollectionScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateCollectionScreenBinding
    private val viewModel: CreateCollectionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CinemaLab)

        binding = ActivityCreateCollectionScreenBinding.inflate(layoutInflater)

        setOnButtonClickListeners()

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
                    finish()
                }
            }
        }
        viewModel.state.observe(this, stateObserver)

        setContentView(binding.root)
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
        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)

        builder.setTitle(getString(R.string.input_incorrect))
        builder.setMessage(message)
        builder.show()
    }

    private fun setOnButtonClickListeners() {
        setOnBackButtonClickListener()
        setOnSaveButtonClickListener()
    }

    private fun setOnBackButtonClickListener() {
        binding.btBack.setOnClickListener {
            finish()
        }
    }

    private fun setOnSaveButtonClickListener() {
        binding.btSave.setOnClickListener {
            viewModel.createCollection(
                binding.etCollectionName.text.toString()
            )
        }
        if(viewModel.state.value == CreateCollectionViewModel.CreateCollectionState.Success) {
            finish()
        }
    }
}