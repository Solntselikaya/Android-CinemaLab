package com.example.cinemalab.domain.repository

import com.example.cinemalab.data.remote.dto.PromotedCoverDto

interface CoverRepository {

    suspend fun getPromotedBanner(token: String): PromotedCoverDto

}