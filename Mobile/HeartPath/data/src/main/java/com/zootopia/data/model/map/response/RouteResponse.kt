package com.zootopia.data.model.map.response

import com.google.gson.annotations.SerializedName

data class RouteResponse(
    @SerializedName("trafast")
    val trafast: List<TrafastResponse>
)
