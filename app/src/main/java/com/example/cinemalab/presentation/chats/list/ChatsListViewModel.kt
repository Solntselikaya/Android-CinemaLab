package com.example.cinemalab.presentation.chats.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.data.remote.dto.toChatModel
import com.example.cinemalab.domain.model.ChatModel
import com.example.cinemalab.domain.usecase.chats.GetChatsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsListViewModel @Inject constructor(
    private val getChatsListUseCase: GetChatsListUseCase
) : ViewModel() {

    sealed class ChatsListState {
        object Initial : ChatsListState()
        object Loading : ChatsListState()
        class Failure(val errorMessage: String) : ChatsListState()
        class Success(val chats: List<ChatModel>) : ChatsListState()
    }

    private val _state = MutableLiveData<ChatsListState>(ChatsListState.Initial)
    var state: LiveData<ChatsListState> = _state

    fun getChats() {
        _state.value = ChatsListState.Loading

        viewModelScope.launch {
            try {
                val discussions = getChatsListUseCase()
                _state.value = ChatsListState.Success(discussions.map { it.toChatModel() })
            } catch (ex: Exception) {
                _state.value = ChatsListState.Failure(ex.message.toString())
            }
        }
    }

    /*fun setSuccess(chat: ChatModel?) {
        val list = mutableListOf<ChatModel>()
        if (chat != null) {
            list.add(chat)
        }
        _state.value = ChatsListState.Success(list)
    }*/
}