package com.zootopia.presentation.login

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "LoginViewModel_HP"
@HiltViewModel
class LoginViewModel @Inject constructor() :
BaseViewModel() {
    fun loginByKakao(context: Context) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            Log.d(TAG, "loginByKakao: here is callback")
            if (error != null) {
                Log.d(TAG, "로그인 실패 $error")
            } else if (token != null) {
                Log.d(TAG, "로그인 성공 ${token.accessToken}")
            }
        }
//        // 카카오톡 설치 확인
//        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context = context)) {
//            // 카카오톡 로그인
//            UserApiClient.instance.loginWithKakaoTalk(context = context) { token, error ->
//                // 로그인 실패 부분
//                if (error != null) {
//                    Log.e(TAG, "로그인 실패 $error")
//                    // 사용자가 취소
//                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled ) {
//                        return@loginWithKakaoTalk
//                    }
//                    // 다른 오류
//                    else {
//                        UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback) // 카카오 이메일 로그인
//                    }
//                }
//                // 로그인 성공 부분
//                else if (token != null) {
//                    Log.e(TAG, "로그인 성공 ${token.accessToken}")
//                }
//            }
//        } else {
//            UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback) // 카카오 이메일 로그인
//        }
    }



}