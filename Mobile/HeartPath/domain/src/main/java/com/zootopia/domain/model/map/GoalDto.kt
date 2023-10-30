package com.zootopia.domain.model.map

data class GoalDto(
    val location: List<Double> = mutableListOf(),
    val dir: Int = 0
)
