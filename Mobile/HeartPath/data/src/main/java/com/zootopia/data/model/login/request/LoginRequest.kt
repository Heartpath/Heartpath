package com.zootopia.data.model.login.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("kakaoToken")
    val kakaoAccessToken: String,
    @SerializedName("fcmToken")
    val fcmToken: String
)