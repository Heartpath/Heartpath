package com.zootopia.data.model.login.response

import com.google.gson.annotations.SerializedName
import com.zootopia.domain.model.login.TokenDto

data class LoginResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: TokenDto?,
)