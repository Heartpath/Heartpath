package com.zootopia.data.model.map.response

import com.google.gson.annotations.SerializedName

data class MapDirectionResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("currentDateTime")
    val currentDateTime: String,
    @SerializedName("route")
    val route: RouteResponse
)
