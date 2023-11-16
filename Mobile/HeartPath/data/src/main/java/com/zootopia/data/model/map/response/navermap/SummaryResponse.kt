package com.zootopia.data.model.map.response.navermap

import com.google.gson.annotations.SerializedName

data class SummaryResponse(
    @SerializedName("start")
    val start: LocationResponse,
    @SerializedName("goal")
    val goal: GoalResponse,
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("bbox")
    val bbox: List<List<Double>>,
    @SerializedName("tollFare")
    val tollFare: Int,
    @SerializedName("taxiFare")
    val taxiFare: Int,
    @SerializedName("fuelPrice")
    val fuelPrice: Int
)
