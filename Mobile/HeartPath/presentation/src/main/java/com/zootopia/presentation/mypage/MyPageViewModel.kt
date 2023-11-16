package com.zootopia.presentation.mypage

import com.zootopia.domain.model.user.FriendDto
import com.zootopia.domain.model.user.UserInfoDto
import com.zootopia.domain.usecase.user.GetFriendListUseCase
import com.zootopia.domain.usecase.user.GetUserInfoUseCase
import com.zootopia.domain.usecase.user.PutOpponentFriendUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getFriendListUseCase: GetFriendListUseCase,
    private val putOpponentFriendUseCase: PutOpponentFriendUseCase,
): BaseViewModel() {
    private val _userInfo = MutableStateFlow(UserInfoDto())
    var userInfo = _userInfo.asStateFlow()

    private val _friendListInfo = MutableSharedFlow<List<FriendDto>>()
    var friendListInfo = _friendListInfo.asSharedFlow()

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

    fun getFriendList() {
        getApiResult(
            block = {
                getFriendListUseCase.invoke()
            },
            success = {result ->
                if (result != null) {
                    _friendListInfo.emit(result)
                }
            }
        )
    }

    fun reportFriend(id: String) {
        getApiResult(
            block = {
                putOpponentFriendUseCase.invoke(opponentID = id)
            },
            success = {
                getFriendList() // 친구 재호출
            }
        )
    }
}