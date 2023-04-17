package com.example.cinemalab.domain.usecase.chats

import android.content.Context
import com.example.cinemalab.data.remote.dto.ChatDto
import com.example.cinemalab.domain.repository.ChatsInfoRepository
import com.example.cinemalab.domain.usecase.token.GetTokenFromLocalStorageUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetChatsListUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: ChatsInfoRepository
) {

    suspend operator fun invoke(): List<ChatDto> {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        val bearerToken = "Bearer ${token.accessToken}"

        return repository.getChats(bearerToken)
    }

}