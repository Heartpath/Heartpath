package com.zootopia.data.model.map.response.tmap

import com.google.gson.annotations.SerializedName


data class FeatureResponse(
    @SerializedName("type")
    val type: String,
    @SerializedName("geometry")
    val geometry: GeometryResponse,
    @SerializedName("properties")
    val properties: PropertiesResponse
)
