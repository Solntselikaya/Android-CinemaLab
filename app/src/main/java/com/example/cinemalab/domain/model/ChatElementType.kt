package com.example.cinemalab.domain.model

import com.example.cinemalab.R

enum class ChatElementType(val itemLayout: Int) {
    DATE(R.layout.item_date),
    MY_MESSAGE_FIRST(R.layout.item_message_my_first),
    MY_MESSAGE(R.layout.item_message_my),
    MESSAGE_FIRST(R.layout.item_message_first),
    MESSAGE(R.layout.item_message)

}