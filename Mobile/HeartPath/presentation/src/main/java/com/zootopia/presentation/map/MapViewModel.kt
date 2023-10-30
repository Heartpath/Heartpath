package com.zootopia.presentation.map

import android.util.Log
import com.zootopia.domain.usecase.map.GetMapDirectionUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "MapViewModel_HP"

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getMapDirectionUseCase: GetMapDirectionUseCase,
) : BaseViewModel() {
    
    
    
    fun getMapDirection() {
        getApiResult(
            block = {
                getMapDirectionUseCase.invoke(
                    "127.1058342,37.359708",
                    "129.075986,35.179470",
                    "trafast"
                )
            },
            success = { result ->
                Log.d(TAG, "getMapDirection: $result")
                // 여기서 result를 사용할 수 있습니다.
            },
        )
    }

}