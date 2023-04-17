package com.example.cinemalab.domain.repository

import com.example.cinemalab.data.remote.dto.*

interface ChatsInfoRepository {

    suspend fun getChats(token: String): List<ChatDto>

    suspend fun getChatInfo(token: String, chatId: String): ChatInfoDto

}