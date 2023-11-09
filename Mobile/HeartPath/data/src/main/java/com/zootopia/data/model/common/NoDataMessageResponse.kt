package com.zootopia.data.model.common

import com.google.gson.annotations.SerializedName

data class NoDataMessageResponse (
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Any?
)