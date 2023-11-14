package com.zootopia.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zootopia.domain.usecase.letter.received.TestFcmUseCase
import com.zootopia.domain.usecase.preference.GetAccessTokenUseCase
import com.zootopia.domain.usecase.preference.GetBgmStateUseCase
import com.zootopia.domain.usecase.preference.GetPermissionRejectedUseCase
import com.zootopia.domain.usecase.preference.SetPermissionRejectedUseCase
import com.zootopia.presentation.readletter.ReadLetterViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainViewModel_HeartPath"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPermissionRejectedUseCase: GetPermissionRejectedUseCase,
    private val setPermissionRejectedUseCase: SetPermissionRejectedUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getBgmStateUseCase: GetBgmStateUseCase
) : ViewModel() {

    private var _isShowPermissionDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isShowPermissionDialog: StateFlow<Boolean>
        get() = _isShowPermissionDialog.asStateFlow()

    private val _accessToken = MutableSharedFlow<String>()
    var accessToken = _accessToken.asSharedFlow()

    private var _bgmState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val bgmState: StateFlow<Boolean> = _bgmState

    fun getPermissionRejected(keys: MutableList<String>) {
        Log.d(TAG, "getPermissionRejected: 들어옴~")
        var isDialog = false
        keys.forEach {
            viewModelScope.launch {
                getPermissionRejectedUseCase.invoke(it).collect{ value ->
                    Log.d(TAG, "getPermissionRejected: $it -> $value")
                    when (value) {
                        0 -> {
                            setPermissionRejected(it, value + 1)
                        }
                        
                        1 -> {
                            setPermissionRejected(it, value + 1)
                        }
                        
                        2 -> {
                            // 다이얼로그 출력
                            isDialog = true
                        }
                    }
                }
            }
        }
        Log.d(TAG, "getPermissionRejected: 다이얼로그 : $isDialog")
        viewModelScope.launch {
            if (isDialog) setIsShowPermissionDialog(true)
        }
    }
    
    fun setPermissionRejected(key: String, stack: Int) {
        viewModelScope.launch {
            setPermissionRejectedUseCase.invoke(key, stack)
        }
    }
    
    suspend fun setIsShowPermissionDialog(value: Boolean) {
        viewModelScope.launch {
            _isShowPermissionDialog.emit(value)
        }
    }

    fun getAccessToken() = viewModelScope.launch {
        _accessToken.emit(getAccessTokenUseCase.invoke().first())
    }


    fun getBgmState() {
        viewModelScope.launch {
            getBgmStateUseCase.invoke(key = "bgm_state").collectLatest { state ->
                _bgmState.update { state }
            }
        }
    }

}
