package com.zootopia.data.model.map.request

import com.google.gson.annotations.SerializedName

data class MapDirectionRequest(
    @SerializedName("start")
    val start: String,
    @SerializedName("goal")
    val goal: String,
    @SerializedName("option")
    val option: String,
)
