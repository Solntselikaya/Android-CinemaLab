package com.example.cinemalab.data.remote.api

import com.example.cinemalab.data.remote.dto.*
import retrofit2.http.*

interface ChatsInfoApi {

    @GET("chats")
    suspend fun getChats(
        @Header("Authorization") token: String
    ): List<ChatDto>

    @GET("chats/{chatId}")
    suspend fun getChatInfo(
        @Header("Authorization") token: String,
        @Path("chatId") chatId: String
    ): ChatInfoDto

}