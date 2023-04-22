package com.example.cinemalab.domain.repository

import com.example.cinemalab.data.remote.dto.ChatDto
import com.example.cinemalab.data.remote.dto.ChatInfoDto

interface ChatsInfoRepository {

    suspend fun getChats(token: String): List<ChatDto>

    suspend fun getChatInfo(token: String, chatId: String): ChatInfoDto

}