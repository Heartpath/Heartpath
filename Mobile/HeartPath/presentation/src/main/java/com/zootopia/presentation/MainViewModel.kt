package com.zootopia.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zootopia.domain.usecase.preference.GetPermissionRejectedUseCase
import com.zootopia.domain.usecase.preference.SetPermissionRejectedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainViewModel_HeartPath"
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPermissionRejectedUseCase: GetPermissionRejectedUseCase,
    private val setPermissionRejectedUseCase: SetPermissionRejectedUseCase,
) : ViewModel() {
    
    private var _isShowPermissionDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isShowPermissionDialog: StateFlow<Boolean>
        get() = _isShowPermissionDialog.asStateFlow()
    
    fun getPermissionRejected(key: String) {
        viewModelScope.launch {
            getPermissionRejectedUseCase.invoke(key).collect { value ->
                Log.d(TAG, "getPermissionRejected: $value")
                when(value) {
                    0 -> {
                        setPermissionRejected(key, value+1)
                    }
                    
                    1 -> {
                        setPermissionRejected(key, value+1)
                    }
                    
                    2 -> {
                        // 다이얼로그 출력
                        setIsShowPermissionDialog(true)
                    }
                }
            }
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
}
