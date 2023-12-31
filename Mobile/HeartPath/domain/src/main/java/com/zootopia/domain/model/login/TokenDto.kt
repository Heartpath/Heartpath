package com.zootopia.domain.model.login

import com.google.gson.annotations.SerializedName

data class TokenDto(
    @SerializedName("AccessToken")
    val accessToken: String = "default",
    @SerializedName("RefreshToken")
    val refreshToken: String = "default",
)