package com.zootopia.data.model.map.response.navermap

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("location")
    val location: List<Double>
)
