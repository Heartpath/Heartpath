package com.zootopia.domain.model.map

data class GuideDto (
    val pointIndex: Int = 0,
    val type: Int = 0,
    val instructions: String = "",
    val distance: Int = 0,
    val duration: Int = 0
)