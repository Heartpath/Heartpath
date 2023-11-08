package com.zootopia.domain.usecase.login

import android.util.Log
import com.zootopia.domain.model.login.TokenDto
import com.zootopia.domain.repository.login.LoginRepository
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(memberId: String,kakaoAccessToken: String, fcmToken: String): TokenDto? {
        Log.d(TAG, "invoke: 여기까지 도달")
        return loginRepository.signup(memberId = memberId, kakaoAccessToken = kakaoAccessToken, fcmToken = fcmToken)
    }
    companion object {
        private const val TAG = "SignupUseCase_HP"
    }
}