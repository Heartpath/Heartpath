package com.zootopia.domain.model.tmap

data class FeatureCollectionDto(
    val type: String = "",
    val features: List<FeatureDto> = mutableListOf()
)
