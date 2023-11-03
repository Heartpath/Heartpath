package com.zootopia.domain.model.tmap

data class FeatureDto(
    val type: String = "",
    val geometry: GeometryDto,
    val properties: PropertiesDto
)
