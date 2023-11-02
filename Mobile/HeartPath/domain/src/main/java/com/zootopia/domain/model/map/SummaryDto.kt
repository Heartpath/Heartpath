package com.zootopia.domain.model.map

data class SummaryDto(
    val start: LocationDto,
    val goal: GoalDto,
    val distance: Int = 0,
    val duration: Int = 0,
    val bbox: List<List<Double>> = mutableListOf(),
    val tollFare: Int = 0,
    val taxiFare: Int = 0,
    val fuelPrice: Int = 0,
)
