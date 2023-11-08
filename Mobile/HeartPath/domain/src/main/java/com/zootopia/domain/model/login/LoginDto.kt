package com.zootopia.domain.model.login

data class LoginDto (
    var kakaoAccessToken: String = "",
    val fcmToken: String = "",
)