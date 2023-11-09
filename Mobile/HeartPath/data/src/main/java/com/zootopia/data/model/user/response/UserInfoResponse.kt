package com.zootopia.data.model.user.response

import com.google.gson.annotations.SerializedName
import com.zootopia.domain.model.user.UserInfoDto

data class UserInfoResponse (
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: UserInfoDto,
)