package com.zootopia.domain.model.letter


import com.google.gson.annotations.SerializedName

data class ReceiveLetterDto(
    @SerializedName("index")
    val index: Int = 0,
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lng")
    val lng: Double = 0.0,
    @SerializedName("location")
    val location: List<String> = listOf(),
    @SerializedName("sender")
    val sender: String = "하참새",
    @SerializedName("time")
    val time: String = "",
)