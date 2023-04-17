package com.example.cinemalab.presentation.chats.list

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemalab.R
import com.example.cinemalab.domain.model.ChatModel


class ChatsRecyclerAdapter(
    private val chats: List<ChatModel>,
    //private val onItemClick: (ChatModel) -> Unit,
) : RecyclerView.Adapter<ChatsRecyclerAdapter.ChatsViewHolder>() {

    class ChatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvChatName)
        val shortName: TextView = itemView.findViewById(R.id.tvChatShortName)
        val lastMessage: TextView = itemView.findViewById(R.id.tvLastMessage)

        val item: View = itemView.findViewById(R.id.item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat, parent, false)
        return ChatsViewHolder(itemView)
    }

    override fun getItemCount() = chats.size

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        holder.name.text = chats[position].chatName
        holder.shortName.text = getChatShortName(chats[position].chatName)

        val userName = getUserName(holder, chats[position].lastMessage?.authorName ?: "Undefined")
        val message = chats[position].lastMessage?.text
        val spannable: Spannable = SpannableString(userName + message)
        spannable.setSpan(
            ForegroundColorSpan(holder.item.context.getColor(R.color.dark_gray)),
            0,
            userName.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        holder.lastMessage.text = spannable

        holder.item.setOnClickListener {
            //onItemClick(chats[position])
        }
    }

    private fun getChatShortName(chatName: String): String {
        val words = chatName.split(" ")
        when(words.size) {
            1 -> return words[0].substring(0, 1).uppercase()
            2 -> return words[0].substring(0, 1).uppercase() + words[1].substring(0, 1).uppercase()
        }
        return ""
    }

    private fun getUserName(holder: ChatsViewHolder, fullName: String): String {
        val words = fullName.split(" ")
        return holder.item.context.getString(R.string.name_and_dots, words[0])
    }

}