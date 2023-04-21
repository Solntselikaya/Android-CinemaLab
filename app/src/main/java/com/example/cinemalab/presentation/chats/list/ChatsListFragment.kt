package com.example.cinemalab.presentation.chats.list

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinemalab.R
import com.example.cinemalab.databinding.FragmentChatsListBinding
import com.example.cinemalab.domain.model.ChatModel
import com.example.cinemalab.domain.model.ShortChatInfoModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatsListFragment : Fragment() {

    private var callback: ChatsListListener? = null

    interface ChatsListListener {
        fun onChatsListExit()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as ChatsListListener
    }

    private lateinit var binding: FragmentChatsListBinding
    private val viewModel: ChatsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_chats_list, container, false)
        binding = FragmentChatsListBinding.bind(view)

        setOnClickListeners()

        val stateObserver = Observer<ChatsListViewModel.ChatsListState> { newState ->
            when (newState) {
                ChatsListViewModel.ChatsListState.Initial -> {
                    viewModel.getChats()
                }
                ChatsListViewModel.ChatsListState.Loading -> {
                    showLoading()
                }
                is ChatsListViewModel.ChatsListState.Failure -> {
                    hideLoading()
                    showErrorDialog(newState.errorMessage)
                }
                is ChatsListViewModel.ChatsListState.Success -> {
                    setupRecyclerView(newState.chats)
                    hideLoading()
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    private fun showLoading() {
        binding.rvChats.isGone = true
        binding.loading.show()
    }

    private fun hideLoading() {
        binding.rvChats.isGone = false
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
    }

    private fun setOnBackButtonClickListener() {
        binding.btBack.setOnClickListener {
            callback?.onChatsListExit()
        }
    }

    private fun setupRecyclerView(chats: List<ChatModel>) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.rvChats.layoutManager = layoutManager
        binding.rvChats.adapter = ChatsRecyclerAdapter(chats) { navigateToChat(it) }
    }

    private fun navigateToChat(chatInfo: ShortChatInfoModel) {
        val action = ChatsListFragmentDirections.actionChatsListFragmentToMessengerFragment(chatInfo)
        findNavController().navigate(action)
    }

}