package com.zootopia.presentation.searchfriend

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.zootopia.domain.model.user.FriendDto
import com.zootopia.domain.model.user.SearchUserInfoDto
import com.zootopia.domain.usecase.user.AddFriendUseCase
import com.zootopia.domain.usecase.user.SearchUserUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFriendViewModel @Inject constructor(
    private val addFriendUseCase: AddFriendUseCase,
    private val searchUserUseCase: SearchUserUseCase,
): BaseViewModel() {
    private val _searchIdValue = MutableStateFlow("")
    var searchIdValue = _searchIdValue.asStateFlow()

    private val _addingFriendId = MutableStateFlow("")
    var addingFriendId = _addingFriendId.asStateFlow()

    private val _searchedFriendInfoList = MutableSharedFlow<List<SearchUserInfoDto>>()
    var searchedFriendInfoList = _searchedFriendInfoList.asSharedFlow()

//    private val _userList = MutableState(listOf<>())

    // 입력 값 갱신
    fun setSearchIdValue(value: String) = viewModelScope.launch {
        _searchIdValue.emit(value)
    }

    // 사용자 검색
    fun searchUser() {
        getApiResult(
            block = {
                searchUserUseCase.invoke(id = _searchIdValue.value, limit = 10, checkFriends = false)
            },
            success = {
                _searchedFriendInfoList.emit(it)
            }
        )
    }
    
    // 사용자 추가
    fun addFriend() = viewModelScope.launch {
        Log.d(TAG, "addFriend: do add friend")
        addFriendUseCase.invoke(id = _addingFriendId.value)
    }

    fun setAddingFriendId(friendId: String) = viewModelScope.launch {
        _addingFriendId.emit(friendId)
    }


    companion object {
        private const val TAG = "SearchFriendViewModel_HP"
    }
}