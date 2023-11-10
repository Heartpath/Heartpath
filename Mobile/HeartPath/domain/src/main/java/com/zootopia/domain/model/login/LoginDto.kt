package com.zootopia.domain.model.login

data class LoginDto (
    val kakaoAccessToken: String = "",
    val fcmToken: String = "",
)