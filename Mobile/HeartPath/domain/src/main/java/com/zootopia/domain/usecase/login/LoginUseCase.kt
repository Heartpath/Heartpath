package com.zootopia.domain.usecase.login

import com.zootopia.domain.model.login.TokenDto
import com.zootopia.domain.repository.login.LoginRepository
import javax.inject.Inject
import kotlin.math.log

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(kakaoAccessToken: String, fcmToken: String): TokenDto {
        return loginRepository.login(kakaoAccessToken = kakaoAccessToken, fcmToken = fcmToken)
    }
}