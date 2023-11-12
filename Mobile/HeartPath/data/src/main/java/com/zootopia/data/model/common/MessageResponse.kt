package com.zootopia.data.model.common

import com.google.gson.annotations.SerializedName

data class MessageResponse(
//    @SerializedName("httpStatus")
//    val httpStatus: String?,
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
)
