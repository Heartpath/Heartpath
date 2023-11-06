package com.zootopia.presentation.map

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zootopia.domain.model.navermap.MapLetterDto
import com.zootopia.domain.model.tmap.FeatureCollectionDto
import com.zootopia.domain.model.tmap.RequestTmapWalkRoadDto
import com.zootopia.domain.usecase.map.GetMapDirectionUseCase
import com.zootopia.domain.usecase.map.RequestTmapWalkRoadUseCase
import com.zootopia.domain.usecase.testUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MapViewModel_HP"

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getMapDirectionUseCase: GetMapDirectionUseCase,
    private val requestTmapWalkRoadUseCase: RequestTmapWalkRoadUseCase,
    private val testUseCase: testUseCase,
) : BaseViewModel() {
    
    // 편지 신고 삭제 신고 클릭 유무
    var isReport = false
    
    // 더미더미더미
    var LetterList = mutableListOf<MapLetterDto>(
        MapLetterDto(1,false, "하동혁님이 보낸 편지1", "2023.11.01", "36.1094880", "128.420560"),
        MapLetterDto(2,false, "하동혁님이 보낸 편지2", "2023.11.01","36.1051004", "128.422769"),
        MapLetterDto(3,false, "하동혁님이 보낸 편지3", "2023.11.01","36.111282119957956", "128.42349987855764 "),
        MapLetterDto(4,false, "하동혁님이 보낸 편지4", "2023.11.01"),
        MapLetterDto(5,false, "하동혁님이 보낸 편지5", "2023.11.01"),
        MapLetterDto(6,false, "하동혁님이 보낸 편지6", "2023.11.01"),
        MapLetterDto(7, false, "하동혁님이 보낸 편지7", "2023.11.01"),
    )
    
    private val _mapLetterList = MutableSharedFlow<MutableList<MapLetterDto>>()
    val mapLetterList: SharedFlow<MutableList<MapLetterDto>>
        get() = _mapLetterList
    
    fun getDummyList() {
        viewModelScope.launch {
            _mapLetterList.emit(LetterList)
        }
    }
    
    var lastLatitude = ""
    var lastLongitude = ""
    fun setLocation(latitude: Double, longitude: Double) {
        lastLatitude = latitude.toString()
        lastLongitude = longitude.toString()
    }
    
    // Tmap
    private val _tmapWalkRoadInfo = MutableSharedFlow<FeatureCollectionDto>()
    val tmapWalkRoadInfo: SharedFlow<FeatureCollectionDto>
        get() = _tmapWalkRoadInfo.asSharedFlow()
    
    @OptIn(ExperimentalCoroutinesApi::class)
    fun resetTmapWalkRoadInfo() {
        _tmapWalkRoadInfo.resetReplayCache()
    }
    
    fun requestTmapWalkRoad(
        mapLetterDto: MapLetterDto
    ) {
        getApiResult(
            block = {
                Log.d(TAG, "requestTmapWalkRoad: Tmap 요청!!!!!!!!!!!!")
                requestTmapWalkRoadUseCase.invoke(
                    RequestTmapWalkRoadDto(
                        startX = lastLongitude,
                        startY = lastLatitude,
                        endX = mapLetterDto.longitude,
                        endY = mapLetterDto.latitude,
                        reqCoordType = "WGS84GEO", // 위경도 표현 타입 코드
                        resCoordType = "WGS84GEO",
                        startName = "내 위치",
                        endName = "편지"
                    )
                )
            },
            success = { result ->
                Log.d(TAG, "requestTmapWalkRoad: ${result.features}")
                _tmapWalkRoadInfo.emit(result)
            }
        )
    }
    // Tmap - END
    
    // 길찾기
    private val _isWorkManager = MutableSharedFlow<Boolean>()
    val isWorkManager: SharedFlow<Boolean>
        get() = _isWorkManager
    
    
    // 임시 사용
    fun test() {
        getApiResult(
            block = {
                testUseCase.invoke()
            },
            success = {
                Log.d(TAG, "test: $it")
            }
        )
    }
    
}