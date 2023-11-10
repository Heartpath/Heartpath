package com.zootopia.domain.model.tmap

data class RequestTmapWalkRoadDto(
    val startX: String = "",
    val startY: String = "",
    val endX: String = "",
    val endY: String = "",
    val reqCoordType: String = "",
    val resCoordType: String = "",
    val startName: String = "",
    val endName: String = ""
)
