package com.zootopia.presentation.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zootopia.domain.model.login.TokenDto
import com.zootopia.domain.usecase.login.CheckIdUseCase
import com.zootopia.domain.usecase.login.LoginUseCase
import com.zootopia.domain.usecase.login.SignupUseCase
import com.zootopia.domain.usecase.preference.SetFcmTokenUseCase
import com.zootopia.domain.usecase.preference.SetKakaoAccessTokenUseCase
import com.zootopia.domain.usecase.preference.SetTokenUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
    
    private val _setTokenResult = MutableSharedFlow<Boolean>()
    var setTokenResult = _setTokenResult.asSharedFlow()


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

    companion object {
        private const val TAG = "LoginViewModel_HP"
    }
}