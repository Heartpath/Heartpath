package com.zootopia.data.model.map.response

import com.google.gson.annotations.SerializedName

data class GoalResponse(
    @SerializedName("location")
    val location: List<Double>,
    @SerializedName("dir")
    val dir: Int
)
