package com.example.cinemalab.presentation.chats.messenger

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cinemalab.R
import com.example.cinemalab.databinding.FragmentMessengerBinding
import com.example.cinemalab.presentation.movie.MovieActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessengerFragment : Fragment() {

    private var callback: MessengerListener? = null

    interface MessengerListener {
        fun onMessengerExit()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as MovieActivity
    }

    private lateinit var binding: FragmentMessengerBinding
    private val viewModel: MessengerViewModel by viewModels()

    private val args: MessengerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_messenger, container, false)
        binding = FragmentMessengerBinding.bind(view)

        setOnClickListeners()

        val stateObserver = Observer<MessengerViewModel.MessengerState> { newState ->
            when(newState){
                MessengerViewModel.MessengerState.Initial -> {
                    hideLoading()
                    setupChatName()
                }
                MessengerViewModel.MessengerState.Loading -> {
                    showLoading()
                }
                is MessengerViewModel.MessengerState.Failure -> {
                    hideLoading()
                    showErrorDialog(newState.errorMessage)
                }
                MessengerViewModel.MessengerState.Success -> {
                    hideLoading()
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    private fun showLoading() {
        binding.grMessageContent.isGone = true
        binding.loading.show()
    }

    private fun hideLoading() {
        binding.grMessageContent.isGone = false
        binding.loading.hide()
    }

    private fun showErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)

        builder.setTitle(getString(R.string.input_incorrect))
        builder.setMessage(message)
        builder.show()
    }

    private fun setupChatName() {
        binding.tvChatName.text = args.chatInfo.name
    }

    private fun setOnClickListeners() {
        setOnBackButtonClickListener()
    }

    private fun setOnBackButtonClickListener() {
        binding.btBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}