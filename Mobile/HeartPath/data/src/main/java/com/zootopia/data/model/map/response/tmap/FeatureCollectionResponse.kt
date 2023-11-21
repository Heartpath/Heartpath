package com.zootopia.data.model.map.response.tmap

import com.google.gson.annotations.SerializedName

data class FeatureCollectionResponse(
    @SerializedName("type")
    val type: String,
    @SerializedName("features")
    val features: List<FeatureResponse>,
)
