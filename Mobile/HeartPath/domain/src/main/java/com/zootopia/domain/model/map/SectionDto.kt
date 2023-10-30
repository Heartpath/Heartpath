package com.zootopia.domain.model.map

data class SectionDto(
    val pointIndex: Int = 0,
    val pointCount: Int = 0,
    val distance: Int = 0,
    val name: String = "",
    val congestion: Int = 0,
    val speed: Int = 0
)

