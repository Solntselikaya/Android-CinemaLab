package com.example.cinemalab.presentation.signin

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cinemalab.R
import com.example.cinemalab.databinding.FragmentSignInBinding
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

        return binding.root
    }

    private fun showErrorDialog() {
        val message = viewModel.validationErrorMessage.value
        val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)

        builder.setTitle(getString(R.string.input_incorrect))
        builder.setMessage(message)
        builder.setOnDismissListener {
            viewModel.clearErrorMessage()
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

            if (viewModel.allFieldsIsValid.value == false)
                showErrorDialog()
            else
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

}