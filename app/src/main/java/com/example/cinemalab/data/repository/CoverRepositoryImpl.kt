package com.example.cinemalab.data.repository

import com.example.cinemalab.data.remote.api.CoverApi
import com.example.cinemalab.data.remote.dto.PromotedCoverDto
import com.example.cinemalab.domain.repository.CoverRepository
import javax.inject.Inject

class CoverRepositoryImpl @Inject constructor(
    private val api: CoverApi
) : CoverRepository {

    override suspend fun getPromotedBanner(token: String): PromotedCoverDto {
        return api.getPromotedBanner(token)
    }

}