package com.zootopia.data.mapper

import com.zootopia.data.model.login.request.LoginRequest
import com.zootopia.data.model.login.response.LoginResponse
import com.zootopia.domain.model.login.LoginDto
import com.zootopia.domain.model.login.TokenDto

fun LoginDto.toData(): LoginRequest {
    return LoginRequest(
        kakaoAccessToken = kakaoAccessToken,
        fcmToken = fcmToken,
    )
}

fun LoginResponse.toDomain(): TokenDto {
    return data
}