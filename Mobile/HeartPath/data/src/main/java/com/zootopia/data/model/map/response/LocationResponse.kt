package com.zootopia.data.model.map.response

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("location")
    val location: List<Double>
)
