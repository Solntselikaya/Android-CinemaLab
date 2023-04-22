package com.example.cinemalab.domain.usecase.cover

import android.content.Context
import com.example.cinemalab.data.remote.dto.PromotedCoverDto
import com.example.cinemalab.domain.repository.CoverRepository
import com.example.cinemalab.domain.usecase.token.GetTokenFromLocalStorageUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetPromotedBannerUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: CoverRepository
) {

    suspend operator fun invoke(): PromotedCoverDto {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        val bearerToken = "Bearer ${token.accessToken}"

        return repository.getPromotedBanner(bearerToken)
    }

}