package com.zootopia.domain.model.letter.uncheckedletter

data class UncheckLetterDto(
    var index: Int,
    var sender: String,
    var senderID: String,
    var time: String,
    var lat: Double,
    var lng: Double,
    var location: List<String>,
    var isSelected: Boolean = false,
)
