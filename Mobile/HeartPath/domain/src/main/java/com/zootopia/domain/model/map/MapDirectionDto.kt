package com.zootopia.domain.model.map

data class MapDirectionDto(
    val code: Int = 0,
    val message: String = "",
    val currentDateTime: String = "",
    val route: RouteDto
)
