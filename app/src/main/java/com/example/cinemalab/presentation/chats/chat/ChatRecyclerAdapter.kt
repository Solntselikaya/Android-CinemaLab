package com.example.cinemalab.presentation.chats.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.cinemalab.R
import com.example.cinemalab.domain.model.ChatElementModel
import com.example.cinemalab.domain.model.ChatElementType

class ChatRecyclerAdapter(
    private val elements: List<ChatElementModel>
) : RecyclerView.Adapter<ViewHolder>() {

    class MyFirstMessageViewHolder(itemView: View) : ViewHolder(itemView) {
        val avatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        val message: TextView = itemView.findViewById(R.id.tvMessageBody)
        val userNameAndTime: TextView = itemView.findViewById(R.id.tvUserNameAndTime)
    }

    class MyMessageViewHolder(itemView: View) : ViewHolder(itemView) {
        val avatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        val message: TextView = itemView.findViewById(R.id.tvMessageBody)
        val userNameAndTime: TextView = itemView.findViewById(R.id.tvUserNameAndTime)
    }

    class FirstMessageViewHolder(itemView: View) : ViewHolder(itemView) {
        val avatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        val message: TextView = itemView.findViewById(R.id.tvMessageBody)
        val userNameAndTime: TextView = itemView.findViewById(R.id.tvUserNameAndTime)
    }

    class MessageViewHolder(itemView: View) : ViewHolder(itemView) {
        val avatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        val message: TextView = itemView.findViewById(R.id.tvMessageBody)
        val userNameAndTime: TextView = itemView.findViewById(R.id.tvUserNameAndTime)
    }

    class DateViewHolder(itemView: View) : ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)

        return when (viewType) {
            ChatElementType.MY_MESSAGE_FIRST.itemLayout -> MyFirstMessageViewHolder(itemView)
            ChatElementType.MY_MESSAGE.itemLayout -> MyMessageViewHolder(itemView)
            ChatElementType.MESSAGE_FIRST.itemLayout -> FirstMessageViewHolder(itemView)
            ChatElementType.MESSAGE.itemLayout -> MessageViewHolder(itemView)
            else -> DateViewHolder(itemView)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return elements[position].type.itemLayout
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ChatElementType.MY_MESSAGE_FIRST.itemLayout -> {
                val viewHolder: MyFirstMessageViewHolder = holder as MyFirstMessageViewHolder

                if ((position + 1) < elements.size && elements[position].authorId == elements[position + 1].authorId) {
                    viewHolder.avatar.visibility = View.INVISIBLE
                } else {
                    setUserAvatar(viewHolder.avatar, elements[position].authorAvatar)
                }

                viewHolder.message.text = elements[position].text
                viewHolder.userNameAndTime.text = elements[position].nameAndTime
            }
            ChatElementType.MY_MESSAGE.itemLayout -> {
                val viewHolder: MyMessageViewHolder = holder as MyMessageViewHolder

                if ((position + 1) < elements.size && elements[position].authorId == elements[position + 1].authorId) {
                    viewHolder.avatar.visibility = View.INVISIBLE
                } else {
                    setUserAvatar(viewHolder.avatar, elements[position].authorAvatar)
                }

                viewHolder.message.text = elements[position].text
                viewHolder.userNameAndTime.text = elements[position].nameAndTime
            }
            ChatElementType.MESSAGE_FIRST.itemLayout -> {
                val viewHolder: FirstMessageViewHolder = holder as FirstMessageViewHolder

                if ((position + 1) < elements.size && elements[position].authorId == elements[position + 1].authorId) {
                    viewHolder.avatar.visibility = View.INVISIBLE
                } else {
                    setUserAvatar(viewHolder.avatar, elements[position].authorAvatar)
                }

                viewHolder.message.text = elements[position].text
                viewHolder.userNameAndTime.text = elements[position].nameAndTime
            }
            ChatElementType.MESSAGE.itemLayout -> {
                val viewHolder: MessageViewHolder = holder as MessageViewHolder

                if ((position + 1) < elements.size && elements[position].authorId == elements[position + 1].authorId) {
                    viewHolder.avatar.visibility = View.INVISIBLE
                } else {
                    setUserAvatar(viewHolder.avatar, elements[position].authorAvatar)
                }

                viewHolder.message.text = elements[position].text
                viewHolder.userNameAndTime.text = elements[position].nameAndTime
            }
            else -> {
                val viewHolder: DateViewHolder = holder as DateViewHolder

                viewHolder.date.text = elements[position].text
            }
        }
    }

    override fun getItemCount() = elements.size

    private fun setUserAvatar(view: ImageView, avatar: String?) {
        if (avatar != null) {
            Glide.with(view).load(avatar).into(view)
        }
    }

}