package com.example.cinemalab.presentation.bottomnav.profilescreen

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
                ProfileViewModel.ProfileState.Initial -> Unit
                ProfileViewModel.ProfileState.Loading -> showLoading()
                is ProfileViewModel.ProfileState.Failure -> {
                    hideLoading()
                    showErrorDialog(newState.errorMessage)
                }
                is ProfileViewModel.ProfileState.Success -> {
                    hideLoading()
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

    /*override fun onStart() {
        viewModel.
        super.onStart()
    }*/

    private fun setOnClickListeners() {
        setOnEditClickListenerAvatar()
        setOnChatsClickListener()
        setOnExitClickListener()
    }

    private fun setOnEditClickListenerAvatar() {
        binding.editAvatar.setOnClickListener {

        }
    }

    private fun setOnChatsClickListener() {
        binding.grChats.setOnClickListener {
            navigateToChatsActivity()
        }
    }

    private fun setOnExitClickListener() {
        binding.btLogout.setOnClickListener {

        }
    }

    private fun showLoading() {
        binding.grProfileContent.isGone = true
        binding.loading.show()
    }

    private fun hideLoading() {
        binding.grProfileContent.isGone = false
        binding.loading.hide()
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

    private fun navigateToChatsActivity() {
        val action = ProfileFragmentDirections.actionProfileFragmentToChatsActivity2()
        findNavController().navigate(action)
    }
}