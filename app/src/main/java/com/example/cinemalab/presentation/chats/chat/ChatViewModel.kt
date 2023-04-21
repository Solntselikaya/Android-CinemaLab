package com.example.cinemalab.presentation.chats.chat

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemalab.R
import com.example.cinemalab.data.remote.dto.toMessageModel
import com.example.cinemalab.domain.model.ChatElementModel
import com.example.cinemalab.domain.model.ChatElementType
import com.example.cinemalab.domain.model.MessageModel
import com.example.cinemalab.domain.usecase.chats.websocket.CloseWebSocketUseCase
import com.example.cinemalab.domain.usecase.chats.websocket.GetMessagesUseCase
import com.example.cinemalab.domain.usecase.chats.websocket.OpenWebSocketUseCase
import com.example.cinemalab.domain.usecase.chats.websocket.SendMessageUseCase
import com.example.cinemalab.domain.usecase.profile.GetProfileInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val openWebSocketUseCase: OpenWebSocketUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val closeWebSocketUseCase: CloseWebSocketUseCase,
    private val getProfileInfoUseCase: GetProfileInfoUseCase
) : ViewModel() {

    sealed class ChatState {
        object Initial : ChatState()
        object Loading : ChatState()
        class Failure(val errorMessage: String) : ChatState()
        object Success : ChatState()
    }

    private val _state = MutableLiveData<ChatState>(ChatState.Initial)
    var state: LiveData<ChatState> = _state

    private val _messageList = MutableLiveData<MutableList<ChatElementModel>>(mutableListOf())
    var messageList: LiveData<MutableList<ChatElementModel>> = _messageList

    var userId: String = ""
    var prevUserId: String = ""
    var prevDate: String = ""

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val todayDate = LocalDate.now().format(formatter)

    fun openWebSocket(chatId: String) {
        _state.value = ChatState.Loading
        viewModelScope.launch {
            try {
                openWebSocketUseCase(chatId)
                getProfileData()
                getMessages()
            } catch (ex: Exception) {
                _state.value = ChatState.Failure(ex.message.toString())
            }
        }

    }

    fun closeWebSocket() {
        viewModelScope.launch {
            closeWebSocketUseCase()
        }
    }

    private fun getProfileData() {
        viewModelScope.launch {
            try {
                userId = getProfileInfoUseCase().userId
            } catch (ex: Exception) {
                _state.value = ChatState.Failure(ex.message.toString())
            }
        }
    }

    private fun getMessages() {
        viewModelScope.launch {
            getMessagesUseCase().collect {
                if (it != null) {
                    convertMessageToChatElement(it.toMessageModel())
                    _state.value = ChatState.Success
                }
            }
        }
    }

    fun sendMessage(message: String) {
        if (message.isNotBlank()) {
            viewModelScope.launch {
                sendMessageUseCase(message)
                _state.value = ChatState.Success
            }
        }
    }

    private fun convertMessageToChatElement(message: MessageModel) {
        if (message.creationDate != prevDate) {
            prevDate = message.creationDate

            _messageList.value?.add(
                if (message.creationDate == todayDate) {
                    ChatElementModel(
                        type = ChatElementType.DATE,
                        text = context.getString(R.string.today)
                    )
                } else {
                    val dateElements = message.creationDate.split('-')
                    val creationDate = dateElements[2] + " " + getMonthName(dateElements[1].toInt())

                    ChatElementModel(
                        type = ChatElementType.DATE,
                        text = creationDate
                    )
                }
            )
        }

        val currUserId = message.authorId
        var messageType = ChatElementType.MESSAGE
        when (currUserId == userId) {
            true -> {
                if (currUserId != prevUserId) {
                    messageType = ChatElementType.MY_MESSAGE_FIRST
                    prevUserId = currUserId
                } else {
                    messageType = ChatElementType.MY_MESSAGE
                }
            }
            false -> {
                if (currUserId != prevUserId) {
                    messageType = ChatElementType.MESSAGE_FIRST
                    prevUserId = currUserId
                } else {
                    messageType = ChatElementType.MESSAGE
                }
            }
        }

        _messageList.value?.add(
            ChatElementModel(
                type = messageType,
                text = message.text,
                nameAndTime = context.getString(
                    R.string.username_and_time,
                    message.authorName,
                    message.creationTime
                ),
                authorAvatar = message.authorAvatar,
                authorId = message.authorId
            )
        )
    }

    private fun getMonthName(monthNum: Int): String {
        val cal: Calendar = Calendar.getInstance()
        val monthDate = SimpleDateFormat("MMMM")
        cal.set(Calendar.MONTH, monthNum)

        return monthDate.format(cal.time)
    }

}