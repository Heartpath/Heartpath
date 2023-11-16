package com.zootopia.data.model.map.response.navermap

import com.google.gson.annotations.SerializedName

data class GuideResponse(
    @SerializedName("pointIndex")
    val pointIndex: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("instructions")
    val instructions: String,
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("duration")
    val duration: Int
)
