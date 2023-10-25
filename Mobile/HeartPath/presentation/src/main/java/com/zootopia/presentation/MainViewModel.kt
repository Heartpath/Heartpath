package com.zootopia.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {
    private var _isShowPermissionDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isShowPermissionDialog: StateFlow<Boolean>
        get() = _isShowPermissionDialog.asStateFlow()
    
    
    fun getPermissionRejected(key: String): Boolean {
//        return preferenceDataSource.getPermissionRejected(key)
        return false
    }
    
    fun setPermissionRejected(key: String) {
//        preferenceDataSource.setPermissionRejected(key, true)
    }
    
    fun getIsShowedPermissionDialog(key: String): Boolean {
//        return preferenceDataSource.getIsShowedPermissionDialog(key)
        return false
    }
    
    fun setIsShowedPermissionDialog(key:String) {
//        preferenceDataSource.setIsShowedPermissionDialog(key, true)
    }
    fun setIsAlreadyShowedDialog(value: Boolean) {
//        preferenceDataSource.setIsAlreadyShowedDialog(value)
    }
    
    fun setIsShowPermissionDialog(value: Boolean) {
        viewModelScope.launch {
            _isShowPermissionDialog.emit(value)
        }
    }
}
