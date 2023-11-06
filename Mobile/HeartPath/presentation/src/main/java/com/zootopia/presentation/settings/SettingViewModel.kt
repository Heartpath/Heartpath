package com.zootopia.presentation.settings

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zootopia.domain.usecase.preference.GetBgmStateUseCase
import com.zootopia.domain.usecase.preference.SetBgmStateUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SettingViewModel_HP"
@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getBgmStateUseCase: GetBgmStateUseCase,
    private val setBgmStateUseCase: SetBgmStateUseCase
): BaseViewModel(){
    private val _switchState = MutableStateFlow<Boolean>(true)
    var switchState = _switchState.asStateFlow()

    fun getBgmState() = viewModelScope.launch {
        Log.d(TAG, "getBgmState: ${switchState.value}")
        getBgmStateUseCase.invoke(key = "bgm_state").collectLatest { state ->
            _switchState.update { state }
            Log.d(TAG, "getBgmState: ${switchState.value}")
        }
    }

    fun changeBgmState() = viewModelScope.launch {
        _switchState.value = !_switchState.value
        Log.d(TAG, "setBgmState: ${switchState.value}")
    }

    fun saveBgmState() = viewModelScope.launch {
        setBgmStateUseCase.invoke(key = "bgm_state", stateValue = switchState.value)
        Log.d(TAG, "setBgmState: ${switchState.value}")
    }
}