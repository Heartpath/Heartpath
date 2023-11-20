package com.zootopia.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zootopia.domain.usecase.preference.GetBgmStateUseCase
import com.zootopia.domain.usecase.preference.GetPermissionRejectedUseCase
import com.zootopia.domain.usecase.preference.SetPermissionRejectedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainViewModel_HeartPath"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPermissionRejectedUseCase: GetPermissionRejectedUseCase,
    private val setPermissionRejectedUseCase: SetPermissionRejectedUseCase,
    private val getBgmStateUseCase: GetBgmStateUseCase,
) : ViewModel() {

    private var _isShowPermissionDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isShowPermissionDialog: StateFlow<Boolean> = _isShowPermissionDialog.asStateFlow()

    private var _bgmState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val bgmState: StateFlow<Boolean> = _bgmState.asStateFlow()

    fun getPermissionRejected(keys: MutableList<String>) {
        var isDialog = false
        keys.forEach {
            // todo : viewModelScope.launch (Dispatchers.IO) { }로 테스트 해보기
            viewModelScope.launch {
                getPermissionRejectedUseCase.invoke(it).collect { value ->
                    Log.d(TAG, "getPermissionRejected: $it -> $value")
                    when (value) {
                        0 -> setPermissionRejected(it, value + 1)
                        1 -> setPermissionRejected(it, value + 1)
                        2 -> isDialog = true // 다이얼로그 출력
                    }
                }
            }
        }

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

    fun getBgmState() {
        viewModelScope.launch {
            getBgmStateUseCase.invoke(key = "bgm_state").collectLatest { state ->
                _bgmState.update { state }
            }
        }
    }
}
