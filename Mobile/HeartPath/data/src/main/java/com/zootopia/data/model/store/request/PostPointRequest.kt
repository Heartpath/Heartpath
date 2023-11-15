package com.zootopia.data.model.store.request

import com.google.gson.annotations.SerializedName

data class PostPointRequest(
    @SerializedName("point")
    val point: Int
)
