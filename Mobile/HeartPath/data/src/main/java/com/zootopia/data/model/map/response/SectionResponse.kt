package com.zootopia.data.model.map.response

import com.google.gson.annotations.SerializedName

data class SectionResponse(
    @SerializedName("pointIndex")
    val pointIndex: Int,
    @SerializedName("pointCount")
    val pointCount: Int,
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("congestion")
    val congestion: Int,
    @SerializedName("speed")
    val speed: Int
)
