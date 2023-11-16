package com.zootopia.presentation.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zootopia.domain.model.login.TokenDto
import com.zootopia.domain.usecase.login.CheckIdUseCase
import com.zootopia.domain.usecase.login.LoginUseCase
import com.zootopia.domain.usecase.login.SignupUseCase
import com.zootopia.domain.usecase.preference.GetAccessTokenUseCase
import com.zootopia.domain.usecase.preference.GetFcmTokenUseCase
import com.zootopia.domain.usecase.preference.GetKakaoAccessTokenUseCase
import com.zootopia.domain.usecase.preference.SetFcmTokenUseCase
import com.zootopia.domain.usecase.preference.SetKakaoAccessTokenUseCase
import com.zootopia.domain.usecase.preference.SetTokenUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val setKakaoAccessTokenUseCase: SetKakaoAccessTokenUseCase,
    private val setFcmTokenUseCase: SetFcmTokenUseCase,
    private val loginUseCase: LoginUseCase,
    private val setTokenUseCase: SetTokenUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
//    private val getKakaoAccessTokenUseCase: GetKakaoAccessTokenUseCase,
//    private val getFcmTokenUseCase: GetFcmTokenUseCase,
) :
    BaseViewModel() {

    private val _kakaoAccessToken = MutableStateFlow("")
    var kakaoAccessToken = _kakaoAccessToken.asStateFlow()

    private val _fcmToken = MutableStateFlow("")
    var fcmToken = _fcmToken.asStateFlow()

    // 로그인 결과
    private val _loginResult = MutableSharedFlow<TokenDto>()
    var loginResult = _loginResult.asSharedFlow()

    private val _accessToken = MutableStateFlow("")
    var accessToken = _accessToken.asStateFlow()

    private val _refreshToken = MutableStateFlow("")
    var refreshToken = _refreshToken.asStateFlow()
    
    private val _setTokenResult = MutableSharedFlow<Boolean>()
    var setTokenResult = _setTokenResult.asSharedFlow()

    private val _checkAutoLogin = MutableSharedFlow<String>()
    var checkAutoLogin = _checkAutoLogin.asSharedFlow()


    fun setKakaoAccessToken(kakaoToken: String) = viewModelScope.launch {
        _kakaoAccessToken.emit(kakaoToken)
        setKakaoAccessTokenUseCase.invoke(accessToken = kakaoToken)
    }

    fun setFCMToken(fcmToken: String) = viewModelScope.launch {
        _fcmToken.emit(fcmToken)
        setFcmTokenUseCase.invoke(token = fcmToken)
    }

    // 로그인 - 카카오 access token 넣어서 전송
    fun login() = viewModelScope.launch {
//        getToken()
        loginUseCase.invoke(kakaoAccessToken = kakaoAccessToken.value, fcmToken = fcmToken.value)
            ?.let { _loginResult.emit(it) }
    }

    fun setToken(token: TokenDto) = viewModelScope.launch {
        Log.d(TAG, "setToken: here")
        _accessToken.emit(token.accessToken)
        _refreshToken.emit(token.refreshToken)
        storeToken()
    }
    // token 저장
    fun storeToken() = viewModelScope.launch {
        Log.d(TAG, "storeToken: here")
        setTokenUseCase.invoke(accessToken = accessToken.value, refreshToken = refreshToken.value).collectLatest {
            _setTokenResult.emit(it)
            Log.d(TAG, "storeToken: store 끝났으면? $it")
        }
    }

    // 자동 로그인 체크
    fun checkAutoLogin() = viewModelScope.launch {
        Log.d(TAG, "checkAutoLogin: 여기호출")
        _checkAutoLogin.emit(getAccessTokenUseCase.invoke().first())
        Log.d(TAG, "checkAutoLogin: $checkAutoLogin")

    }

//    fun getToken() = viewModelScope.launch {
//        Log.d(TAG, "getToken: ㅇㅕ기도달")
//        launch {
//            getKakaoAccessTokenUseCase.invoke().collectLatest {
//                _kakaoAccessToken.emit(it)
//            }
//        }
//        Log.d(TAG, "getToken: kakao ${kakaoAccessToken.value}")
//        launch {
//            getFcmTokenUseCase.invoke().collectLatest {
//                _fcmToken.emit(it)
//            }
//        }
//        Log.d(TAG, "getToken: fcm ${fcmToken.value}")
//    }

    companion object {
        private const val TAG = "LoginViewModel_HP"
    }
}