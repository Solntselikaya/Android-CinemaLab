package com.example.cinemalab.presentation.authscreens.signinscreen

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.cinemalab.R
import com.example.cinemalab.databinding.FragmentSignInBinding
import com.example.cinemalab.presentation.bottomnavscreens.BottomNavActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)
        binding = FragmentSignInBinding.bind(view)

        setOnButtonsClickListeners()
        val stateObserver = Observer<SignInViewModel.SignInState> { newState ->
            when(newState) {
                SignInViewModel.SignInState.Initial -> {
                    binding.progressBar.isVisible = false
                    binding.btSignIn.isEnabled = true
                    binding.btRegistration.isEnabled = true
                }
                SignInViewModel.SignInState.Loading -> showLoading()
                is SignInViewModel.SignInState.Failure -> showErrorDialog(newState.errorMessage)
                is SignInViewModel.SignInState.Success -> navigateToMainScreen()
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.btSignIn.isEnabled = false
        binding.btRegistration.isEnabled = false
    }

    private fun showErrorDialog(message: String) {
        binding.progressBar.isVisible = false
        val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)

        builder.setTitle(getString(R.string.input_incorrect))
        builder.setMessage(message)
        builder.setOnDismissListener {
            viewModel.exitAlertDialog()
        }
        builder.show()
    }

    private fun setOnButtonsClickListeners() {
        setOnSignInButtonClickListener()
        setOnRegistrationButtonClickListener()
    }

    private fun setOnSignInButtonClickListener() {
        binding.btSignIn.setOnClickListener {

            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.checkIfFieldsIsValid(
                email,
                password
            )

            if (viewModel.allFieldsIsValid.value == true)
                viewModel.login(email, password)
        }
    }

    private fun setOnRegistrationButtonClickListener() {
        binding.btRegistration.setOnClickListener {
            navigateToSignUpScreen()
        }
    }

    private fun navigateToSignUpScreen() {
        findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
    }

    private fun navigateToMainScreen() {
        binding.progressBar.isVisible = false
        findNavController().navigate(R.id.action_signInFragment_to_bottomNavActivity2)

        /*val intent = Intent(this@SignInFragment.requireContext(), BottomNavActivity::class.java)
        startActivity(intent)*/
    }

}