package com.zootopia.data.mapper

import com.zootopia.data.model.common.MessageResponse
import com.zootopia.data.model.login.request.LoginRequest
import com.zootopia.data.model.login.request.SignupRequest
import com.zootopia.data.model.login.response.CheckIdResponse
import com.zootopia.data.model.login.response.LoginResponse
import com.zootopia.domain.model.login.LoginDto
import com.zootopia.domain.model.login.SignupDto
import com.zootopia.domain.model.login.TokenDto

fun LoginDto.toData(): LoginRequest {
    return LoginRequest(
        kakaoAccessToken = kakaoAccessToken,
        fcmToken = fcmToken,
    )
}

fun LoginResponse.toDomain(): TokenDto? {
    return data
}

fun CheckIdResponse.toDomain(): Boolean {
    return data
}

fun SignupDto.toData(): SignupRequest {
    return SignupRequest(
        memberID = memberId,
        kakaoToken = kakaoAccessToken,
        fcmToken = fcmToken
    )
}
