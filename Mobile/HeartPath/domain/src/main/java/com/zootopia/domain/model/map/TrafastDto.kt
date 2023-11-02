package com.zootopia.domain.model.map

data class TrafastDto(
    val summary: SummaryDto,
    val path: List<List<Double>> = mutableListOf(),
    val section: List<SectionDto> = mutableListOf(),
    val guide: List<GuideDto> = mutableListOf()
)
