package com.example.cinemalab.presentation.chats.chat

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinemalab.R
import com.example.cinemalab.databinding.FragmentChatBinding
import com.example.cinemalab.domain.model.ChatElementModel
import com.example.cinemalab.presentation.movie.MovieActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private var callback: MessengerListener? = null

    interface MessengerListener {
        fun onMessengerExit()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as MovieActivity
    }

    private lateinit var binding: FragmentChatBinding
    private val viewModel: ChatViewModel by viewModels()

    private val args: ChatFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        binding = FragmentChatBinding.bind(view)

        val stateObserver = Observer<ChatViewModel.ChatState> { newState ->
            when (newState) {
                ChatViewModel.ChatState.Initial -> {
                    hideLoading()
                    setupChatName()
                }
                ChatViewModel.ChatState.Loading -> {
                    showLoading()
                }
                is ChatViewModel.ChatState.Failure -> {
                    hideLoading()
                    showErrorDialog(newState.errorMessage)
                }
                ChatViewModel.ChatState.Success -> {
                    hideLoading()
                    loadMessages()
                    setOnClickListeners()
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    override fun onStart() {
        viewModel.openWebSocket(args.chatInfo.id)
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
        viewModel.closeWebSocket()
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

    private fun loadMessages() {
        val messageObserver = Observer<MutableList<ChatElementModel>> { messages ->
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager.stackFromEnd = true

            binding.rvMessenger.layoutManager = layoutManager
            binding.rvMessenger.adapter = ChatRecyclerAdapter(messages)
        }
        viewModel.messageList.observe(viewLifecycleOwner, messageObserver)
    }

    private fun setOnClickListeners() {
        setOnBackButtonClickListener()
        setOnSendButtonClickListener()
    }

    private fun setOnBackButtonClickListener() {
        binding.btBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setOnSendButtonClickListener() {
        binding.btSendMessage.setOnClickListener {
            val message = binding.etMessage.text.toString()
            viewModel.sendMessage(message)
            binding.etMessage.setText("")
        }
    }

}