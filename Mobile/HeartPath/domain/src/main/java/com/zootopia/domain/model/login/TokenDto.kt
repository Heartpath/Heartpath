package com.zootopia.domain.model.login

import com.google.gson.annotations.SerializedName

data class TokenDto(
    @SerializedName("AccessToken")
    val accessToken: String = "",
    @SerializedName("RefreshToken")
    val refreshToken: String = "",
)