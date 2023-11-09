package com.zootopia.data.model.login.request


import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("fcmToken")
    val fcmToken: String,
    @SerializedName("kakaoToken")
    val kakaoToken: String,
    @SerializedName("memberID")
    val memberID: String
)