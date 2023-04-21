package com.example.cinemalab.presentation.auth.signupscreen

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.cinemalab.R
import com.example.cinemalab.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        binding = FragmentSignUpBinding.bind(view)

        setOnButtonsClickListeners()

        val stateObserver = Observer<SignUpViewModel.SignUpState> { newState ->
            when (newState) {
                SignUpViewModel.SignUpState.Initial -> {
                    binding.progressBar.isVisible = false
                    binding.btDoRegistration.isEnabled = true
                    binding.btHaveAccount.isEnabled = true
                }
                SignUpViewModel.SignUpState.Loading -> showLoading()
                is SignUpViewModel.SignUpState.Failure -> {
                    binding.progressBar.isVisible = false
                    showErrorDialog(newState.errorMessage)
                }
                is SignUpViewModel.SignUpState.Success -> {
                    binding.progressBar.isVisible = false
                    navigateToMainScreen()
                }
                SignUpViewModel.SignUpState.Navigate -> {
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.signInFragment, true)
                        .build()
                    val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
                    findNavController().navigate(action, navOptions)
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.btDoRegistration.isEnabled = false
        binding.btHaveAccount.isEnabled = false
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

    private fun setOnButtonsClickListeners() {
        setOnRegistrationButtonClickListener()
        setOnHaveAccountButtonClickListener()
    }

    private fun setOnRegistrationButtonClickListener() {
        binding.btDoRegistration.setOnClickListener {

            val name = binding.etName.text.toString()
            val surname = binding.etSurname.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val passwordRep = binding.etPasswordRepeat.text.toString()
            viewModel.checkIfFieldsIsValid(
                name,
                surname,
                email,
                password,
                passwordRep
            )

            if (viewModel.allFieldsIsValid.value == true) {
                viewModel.register(
                    name,
                    surname,
                    email,
                    password
                )
            }
        }
    }

    private fun setOnHaveAccountButtonClickListener() {
        binding.btHaveAccount.setOnClickListener {
            navigateToSignInScreen()
        }
    }

    private fun navigateToSignInScreen() {
        findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
    }

    private fun navigateToMainScreen() {
        binding.progressBar.isVisible = false
        findNavController().navigate(R.id.action_signUpFragment_to_bottomNavActivity2)
    }

}