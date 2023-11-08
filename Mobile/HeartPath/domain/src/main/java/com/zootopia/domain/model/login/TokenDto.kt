package com.zootopia.domain.model.login

data class TokenDto(
    val accessToken: String = "",
    val refreshToken: String = "",
)