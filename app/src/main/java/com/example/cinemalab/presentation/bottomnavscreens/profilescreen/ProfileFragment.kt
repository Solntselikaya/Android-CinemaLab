package com.example.cinemalab.presentation.bottomnavscreens.profilescreen

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.cinemalab.R
import com.example.cinemalab.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        binding = FragmentProfileBinding.bind(view)

        setOnClickListeners()

        val stateObserver = Observer<ProfileViewModel.ProfileState> { newState ->
            when(newState) {
                ProfileViewModel.ProfileState.Initial -> {
                    //binding.progressBar.isVisible = false
                    //binding.loading.hide()
                    binding.loadingScreen.isGone = true
                    viewModel.getProfileInfo()
                }
                ProfileViewModel.ProfileState.Loading -> showLoading()
                is ProfileViewModel.ProfileState.Failure -> {
                    //binding.progressBar.isVisible = false
                    //binding.loading.hide()
                    binding.loadingScreen.isGone = true
                    showErrorDialog(newState.errorMessage)
                }
                is ProfileViewModel.ProfileState.Success -> {
                    //binding.progressBar.isVisible = false
                    //binding.loading.hide()
                    binding.loadingScreen.isGone = true
                    putProfileInfo(
                        newState.profileInfo.firstName,
                        newState.profileInfo.lastName,
                        newState.profileInfo.email,
                        newState.profileInfo.avatar
                    )
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    private fun setOnClickListeners() {
        binding.editAvatar.setOnClickListener {

        }
    }

    private fun showLoading() {
        //binding.progressBar.isVisible = true
        //binding.loading.show()
        binding.loadingScreen.isGone = false
        binding.btLogout.isEnabled = false
    }

    private fun putProfileInfo(
        name: String,
        surname: String,
        email: String,
        avatarUrl: String
    ) {
        Glide.with(this).load(avatarUrl).into(binding.avatar)
        binding.name.text = getString(R.string.name_and_surname, name, surname)
        binding.email.text = email
    }

    private fun showErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)

        builder.setTitle(getString(R.string.input_incorrect))
        builder.setMessage(message)
        builder.setOnDismissListener {
            viewModel.exitAlertDialog()
        }
        builder.show()
    }
}