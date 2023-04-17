package com.example.cinemalab.domain.model

import com.example.cinemalab.R

enum class AgeLimitEnum(
    val value: Int,
    val color: Int
) {
    Eighteen(R.string.age_limit_18, R.color.age_limit_18),
    Sixteen(R.string.age_limit_16, R.color.age_limit_16),
    Twelve(R.string.age_limit_12, R.color.age_limit_12),
    Six(R.string.age_limit_6, R.color.age_limit_6),
    Zero(R.string.age_limit_0, R.color.age_limit_0),
}