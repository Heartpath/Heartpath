package com.zootopia.presentation.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LoginViewModel_HP"
@HiltViewModel
class LoginViewModel @Inject constructor() :
BaseViewModel() {

    private val _kakaoAccessToken = MutableStateFlow("")
    var kakaoAccessToken = _kakaoAccessToken.asStateFlow()

    private val _newId = MutableStateFlow("")
    var newId = _newId.asStateFlow()

    private val _checkIdDone = MutableStateFlow(false)
    var checkIdDone = _checkIdDone.asStateFlow()

    fun loginByKakao(context: Context) {
        val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            Log.d(TAG, "loginByKakao: here is callback")
            if (error != null) {
                Log.d(TAG, "로그인 실패 $error")
            } else if (token != null) {
                Log.d(TAG, "로그인 성공 ${token.accessToken}")
                viewModelScope.launch {
                    // 카카오 access token 값 넣어주기
                    _kakaoAccessToken.emit(token.accessToken)
                    login()
                }
            }
        }
        // 카카오톡 설치 확인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context = context)) {
            // 카카오톡 로그인
            UserApiClient.instance.loginWithKakaoTalk(context = context) { token, error ->
                // 로그인 실패 부분
                if (error != null) {
                    Log.e(TAG, "로그인 실패 $error")
                    // 사용자가 취소
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled ) {
                        return@loginWithKakaoTalk
                    }
                    // 다른 오류
                    else {
                        UserApiClient.instance.loginWithKakaoAccount(context = context, callback = kakaoCallback) // 카카오 이메일 로그인
                    }
                }
                // 로그인 성공 부분
                else if (token != null) {
                    Log.d(TAG, "로그인 성공 ${token.accessToken}")
                    viewModelScope.launch {
                        // 카카오 access token 값 넣어주기
                        _kakaoAccessToken.emit(token.accessToken)
                        login()
                    }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context = context, callback = kakaoCallback) // 카카오 이메일 로그인
        }
    }


    // 로그인 - 카카오 access token 넣어서 전송
    fun login() = viewModelScope.launch {
        // TODO: 카카오 access token 넣어서 서버에 로그인 요청

    }

    // id 입력 값 수정
    fun setNewId(value: String) = viewModelScope.launch {
        _newId.emit(value)
        Log.d(TAG, "setNewId: ${newId.value}")
    }

    // 아이디 중복 체크
    fun duplicateCheckId() = viewModelScope.launch {
        // 만약에 중복 되지 않았으면 true 값으로 변경

    }

    fun setCheckIdDone(value: Boolean) = viewModelScope.launch {
        _checkIdDone.emit(value)
        Log.d(TAG, "setCheckIdDone: ${checkIdDone.value}")
    }

    // 회원가입 요청
    fun signUp() = viewModelScope.launch {

    }




}