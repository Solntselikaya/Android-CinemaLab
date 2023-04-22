package com.example.cinemalab.data.repository

import com.example.cinemalab.data.remote.api.ChatsInfoApi
import com.example.cinemalab.data.remote.dto.ChatDto
import com.example.cinemalab.data.remote.dto.ChatInfoDto
import com.example.cinemalab.domain.repository.ChatsInfoRepository
import javax.inject.Inject

class ChatsInfoRepositoryImpl @Inject constructor(
    private val api: ChatsInfoApi
) : ChatsInfoRepository {

    override suspend fun getChats(token: String): List<ChatDto> {
        return api.getChats(token)
    }

    override suspend fun getChatInfo(token: String, chatId: String): ChatInfoDto {
        return api.getChatInfo(token, chatId)
    }

}