package com.zootopia.domain.model.login

data class SignupDto (
    val memberId: String = "",
    val kakaoAccessToken: String = "",
    val fcmToken: String = "",
)