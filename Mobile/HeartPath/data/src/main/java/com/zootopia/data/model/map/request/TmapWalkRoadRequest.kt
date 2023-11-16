package com.zootopia.data.model.map.request

import com.google.gson.annotations.SerializedName

data class TmapWalkRoadRequest(
    @SerializedName("startX")
    val startX: String,
    @SerializedName("startY")
    val startY: String,
    @SerializedName("endX")
    val endX: String,
    @SerializedName("endY")
    val endY: String,
    @SerializedName("reqCoordType")
    val reqCoordType: String,
    @SerializedName("resCoordType")
    val resCoordType: String,
    @SerializedName("startName")
    val startName: String,
    @SerializedName("endName")
    val endName: String
)
