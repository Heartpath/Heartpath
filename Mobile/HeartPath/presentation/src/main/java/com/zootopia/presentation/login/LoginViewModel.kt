package com.zootopia.presentation.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.zootopia.domain.model.login.TokenDto
import com.zootopia.domain.usecase.login.CheckIdUseCase
import com.zootopia.domain.usecase.login.LoginUseCase
import com.zootopia.domain.usecase.login.SignupUseCase
import com.zootopia.domain.usecase.preference.GetFcmTokenUseCase
import com.zootopia.domain.usecase.preference.GetKakaoAccessTokenUseCase
import com.zootopia.domain.usecase.preference.SetFcmTokenUseCase
import com.zootopia.domain.usecase.preference.SetKakaoAccessTokenUseCase
import com.zootopia.domain.usecase.preference.SetTokenUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val setKakaoAccessTokenUseCase: SetKakaoAccessTokenUseCase,
    private val setFcmTokenUseCase: SetFcmTokenUseCase,
    private val loginUseCase: LoginUseCase,
    private val checkIdUseCase: CheckIdUseCase,
    private val signupUseCase: SignupUseCase,
    private val setTokenUseCase: SetTokenUseCase,
) :
    BaseViewModel() {

    private val _kakaoAccessToken = MutableStateFlow("")
    var kakaoAccessToken = _kakaoAccessToken.asStateFlow()

    private val _fcmToken = MutableStateFlow("")
    var fcmToken = _fcmToken.asStateFlow()

    // 로그인 결과
    private val _loginResult = MutableSharedFlow<TokenDto>()
    var loginResult = _loginResult.asSharedFlow()

    private val _newId = MutableStateFlow("")
    var newId = _newId.asStateFlow()

    private val _checkIdResult = MutableSharedFlow<Boolean>()
    var checkIdResult = _checkIdResult.asSharedFlow()

    private val _checkIdDone = MutableStateFlow(false)
    var checkIdDone = _checkIdDone.asStateFlow()

    // 회원가입 결과
    private val _signupResult = MutableSharedFlow<TokenDto>()
    var signupResult = _signupResult.asSharedFlow()

    private val _accessToken = MutableStateFlow("")
    var accessToken = _accessToken.asStateFlow()

    private val _refreshToken = MutableStateFlow("")
    var refreshToken = _refreshToken.asStateFlow()

    fun loginByKakao(context: Context) {
        val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            Log.d(TAG, "loginByKakao: here is callback")
            if (error != null) {
                Log.d(TAG, "로그인 실패 $error")
            } else if (token != null) {
                Log.d(TAG, "웹으로 카카오 로그인 성공 kakao access token: ${token.accessToken}")
                // Firebase FCM token 가지고 오기
                FirebaseMessaging.getInstance().token.addOnCompleteListener { fcmTask ->
                    Log.d(TAG, "FCM token result: ${fcmTask.result}")
                    if (fcmTask.isSuccessful) {
                        // fcm 토큰 받아오기 성공
                        viewModelScope.launch {
                            // 카카오 access token 값 넣어주기
                            setFcmTokenUseCase.invoke(fcmTask.result)
                            setKakaoAccessTokenUseCase.invoke(token.accessToken)
                            _kakaoAccessToken.emit(token.accessToken)
                            _fcmToken.emit(fcmTask.result)
                            login()

                        }
                    } else {
                        // fcm 토큰 받아 오기 실패
                        return@addOnCompleteListener
                    }
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
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    // 다른 오류
                    else {
                        UserApiClient.instance.loginWithKakaoAccount(
                            context = context,
                            callback = kakaoCallback
                        ) // 카카오 이메일 로그인
                    }
                }
                // 로그인 성공 부분
                else if (token != null) {
                    Log.d(TAG, "카카오 앱으로 로그인 성공 kakao access token: ${token.accessToken}")
                    FirebaseMessaging.getInstance().token.addOnCompleteListener { fcmTask ->
                        Log.d(TAG, "FCM token result: ${fcmTask.result}")
                        if (fcmTask.isSuccessful) {
                            // fcm 토큰 가지고 오기 성공
                            viewModelScope.launch {
                                // 카카오 access token 값 넣어주기
                                setFcmTokenUseCase.invoke(fcmTask.result)
                                setKakaoAccessTokenUseCase.invoke(token.accessToken)
                                _kakaoAccessToken.emit(token.accessToken)
                                _fcmToken.emit(fcmTask.result)
                                login()
                            }
                        } else {
                            // fcm 토큰 가지고 오기 실패
                            return@addOnCompleteListener
                        }
                    }

                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(
                context = context,
                callback = kakaoCallback
            ) // 카카오 이메일 로그인
        }
    }


    // 로그인 - 카카오 access token 넣어서 전송
    fun login() = viewModelScope.launch {
        loginUseCase.invoke(kakaoAccessToken = kakaoAccessToken.value, fcmToken = fcmToken.value)
            ?.let { _loginResult.emit(it) }
    }

    // id 입력 값 수정
    fun setNewId(value: String) = viewModelScope.launch {
        _newId.emit(value)
        Log.d(TAG, "setNewId: ${newId.value}")
    }

    // 아이디 중복 체크
    fun duplicateCheckId() = viewModelScope.launch {
        // 만약에 중복 되지 않았으면 true 값으로 변경
        Log.d(TAG, "duplicateCheckId: check button clicked")
        _checkIdResult.emit(checkIdUseCase.invoke(newId.value))
    }

    fun setCheckIdDone(value: Boolean) = viewModelScope.launch {
        _checkIdDone.emit(value)
        Log.d(TAG, "setCheckIdDone: ${checkIdDone.value}")
    }

    // 회원가입 요청
    fun signUp() = viewModelScope.launch {
        Log.d(TAG, "signUp: ${kakaoAccessToken.value} ${fcmToken.value}")
        signupUseCase.invoke(
            memberId = newId.value,
            kakaoAccessToken = kakaoAccessToken.value,
            fcmToken = fcmToken.value
        )
            ?.let { _signupResult.emit(it) }

    }

    fun setToken(token: TokenDto) = viewModelScope.launch {
        Log.d(TAG, "setToken: here")
        _accessToken.emit(token.accessToken)
        _refreshToken.emit(token.refreshToken)
    }
    // token 저장
    fun storeToken() = viewModelScope.launch {
        Log.d(TAG, "storeToken: here")
        setTokenUseCase.invoke(accessToken = accessToken.value, refreshToken = refreshToken.value)
    }

    companion object {
        private const val TAG = "LoginViewModel_HP"
    }
}