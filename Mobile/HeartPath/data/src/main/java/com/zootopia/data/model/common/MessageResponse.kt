package com.zootopia.data.model.common

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
)