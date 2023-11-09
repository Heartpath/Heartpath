package com.zootopia.presentation.pointhistory

import com.zootopia.domain.model.user.PointDto
import com.zootopia.domain.usecase.user.GetPointInfoUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PointHistoryViewModel @Inject constructor(
    private val getPointInfoUseCase: GetPointInfoUseCase
): BaseViewModel() {
    private val _pointInfoList = MutableSharedFlow<List<PointDto>>()
    var pointInfoList = _pointInfoList.asSharedFlow()

    fun getPointInfoList() {
        getApiResult (
            block = {
                getPointInfoUseCase.invoke()
            },
            success = {result ->
                if (result != null) {
                    _pointInfoList.emit(result)
                }
            }
        )
    }
}