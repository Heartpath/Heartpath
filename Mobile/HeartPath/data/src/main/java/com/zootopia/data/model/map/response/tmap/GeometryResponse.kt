package com.zootopia.data.model.map.response.tmap

import com.google.gson.annotations.SerializedName

data class GeometryResponse(
    @SerializedName("type")
    val type: String,
    @SerializedName("coordinates")
    val coordinates: List<*>?
)

//sealed class GeometryResponse {
//    data class Point(
//        val type: String,
//        val coordinates: List<Double>
//    ) : GeometryResponse()
//
//    data class LineString(
//        val type: String,
//        val coordinates: List<List<Double>>
//    ) : GeometryResponse()
//}
