package com.zootopia.data.model.letter.request

import com.google.gson.annotations.SerializedName

data class LetterPlacedRequest(
    @SerializedName("id")
    val id: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double,
)
