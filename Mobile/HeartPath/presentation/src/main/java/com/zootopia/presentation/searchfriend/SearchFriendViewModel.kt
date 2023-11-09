package com.zootopia.presentation.searchfriend

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFriendViewModel @Inject constructor(

): BaseViewModel() {
    private val _searchIdValue = MutableStateFlow("")
    var searchIdValue = _searchIdValue.asStateFlow()

//    private val _userList = MutableState(listOf<>())

    // 입력 값 갱신
    fun setSearchIdValue(value: String) = viewModelScope.launch {
        _searchIdValue.emit(value)
    }

    // 사용자 검색
    fun searchUser() = viewModelScope.launch {
        Log.d(TAG, "searchUser: do search")
    }
    
    // 사용자 추가
    fun addFriend() = viewModelScope.launch {
        Log.d(TAG, "addFriend: do add friend")
    }

    companion object {
        private const val TAG = "SearchFriendViewModel_HP"
    }
}