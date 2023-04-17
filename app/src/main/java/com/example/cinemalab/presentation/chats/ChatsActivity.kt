package com.example.cinemalab.presentation.chats

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.example.cinemalab.R
import com.example.cinemalab.domain.model.ChatModel
import com.example.cinemalab.presentation.chats.list.ChatsListFragment
import com.example.cinemalab.presentation.chats.messenger.MessengerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatsActivity :
    AppCompatActivity(),
    ChatsListFragment.ChatsListListener,
    MessengerFragment.MessengerListener
{

    //private val args: ChatsActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CinemaLab)
        setContentView(R.layout.activity_chats)
    }

    override fun onChatsListExit() {
        finish()
    }

    override fun onMessengerExit() {
        finish()
    }

    /*fun getChatsList(): ChatModel? {
        return args.chatInfo
    }*/
}