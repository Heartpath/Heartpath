package com.zootopia.presentation.map

import android.util.Log
import com.zootopia.domain.model.map.MapDirectionDto
import com.zootopia.domain.usecase.map.GetMapDirectionUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

private const val TAG = "MapViewModel_HP"

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getMapDirectionUseCase: GetMapDirectionUseCase,
) : BaseViewModel() {
    
    private val _mapDirectionInfo = MutableSharedFlow<MapDirectionDto>()
    val mapDirectionInfo: SharedFlow<MapDirectionDto>
        get() = _mapDirectionInfo.asSharedFlow()
    
    fun getMapDirection() {
        getApiResult(
            block = {
                getMapDirectionUseCase.invoke(
                    "128.4164631,36.107114,",
                    "128.418643,36.1066181",
                    "trafast"
                )
            },
            success = { result ->
                Log.d(TAG, "getMapDirection: $result")
                _mapDirectionInfo.emit(result)
                // 여기서 result를 사용할 수 있습니다.
            },
        )
    }

}