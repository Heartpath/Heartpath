package com.zootopia.data.model.map.response.navermap

import com.google.gson.annotations.SerializedName

data class RouteResponse(
    @SerializedName("trafast")
    val trafast: List<TrafastResponse>
)
