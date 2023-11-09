package com.zootopia.presentation.mypage

import com.zootopia.domain.model.user.UserInfoDto
import com.zootopia.domain.usecase.user.GetUserInfoUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
): BaseViewModel() {
    private val _userInfo = MutableStateFlow(UserInfoDto())
    var userInfo = _userInfo.asStateFlow()

    fun getUserInfo() {
        getApiResult(
            block = {
                getUserInfoUseCase.invoke()
            },
            success = { result ->
                if (result != null) {
                    _userInfo.emit(result)
                }
            }
        )
    }
}