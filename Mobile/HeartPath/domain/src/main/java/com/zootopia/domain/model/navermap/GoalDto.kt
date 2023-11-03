package com.zootopia.domain.model.navermap

data class GoalDto(
    val location: List<Double> = mutableListOf(),
    val dir: Int = 0
)
