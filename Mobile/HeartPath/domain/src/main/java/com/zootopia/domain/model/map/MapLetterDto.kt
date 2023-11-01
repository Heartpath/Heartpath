package com.zootopia.domain.model.map

data class MapLetterDto(
    val uid:Int = 0,
    var isSelected:Boolean = false,
    var title: String = "",
    var time: String = "",
    var latitude: String = "",
    var longitude: String = "",
)
