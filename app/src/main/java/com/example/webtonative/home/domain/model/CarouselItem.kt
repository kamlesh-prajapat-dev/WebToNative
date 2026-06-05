package com.example.webtonative.home.domain.model

import androidx.annotation.DrawableRes

data class CarouselItem(
    val id: Int,
    @field:DrawableRes val imageResId: Int,
    val contentDescription: String
)