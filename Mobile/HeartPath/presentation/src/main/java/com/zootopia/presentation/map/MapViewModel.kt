package com.zootopia.presentation.map

import android.location.Location
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
        MapLetterDto(1, false, "하동혁님이 보낸 편지1", "2023.11.01", "36.1094880", "128.420560"),
        MapLetterDto(2, false, "하동혁님이 보낸 편지2", "2023.11.01", "36.1051004", "128.422769"),
        MapLetterDto(3, false, "하동혁님이 보낸 편지3", "2023.11.01", "36.111282119957956", "128.42349987855764"),
        MapLetterDto(4, false, "우리집", "2023.11.01", "36.121949856660855", "128.38053050168602"),
    )

    private val _mapLetterList = MutableSharedFlow<MutableList<MapLetterDto>>()
    val mapLetterList: SharedFlow<MutableList<MapLetterDto>>
        get() = _mapLetterList

    fun getDummyList() {
        viewModelScope.launch {
            _mapLetterList.emit(LetterList)
        }
    }

    // user posi
    var lastLatitude: Double = 0.0
    var lastLongitude: Double = 0.0
    val lastLocation = Location("userProvider")
    fun setLocation(latitude: Double, longitude: Double) {
        lastLatitude = latitude
        lastLongitude = longitude
    }
    fun makeUserLocataion() {
        lastLocation.latitude = lastLatitude
        lastLocation.longitude = lastLongitude
    }

    // marker posi
    var dist: String = "--m"
    var goalLatitude: Double = 0.0
    var goalLongitude: Double = 0.0
    val goalLocation = Location("goalProvider")

    fun makeGoalLocataion() {
        goalLocation.latitude = goalLatitude
        goalLocation.longitude = goalLongitude
    }

    // 길찾기 상태
    var isStartWalk = false
    private val _isWorkManager = MutableSharedFlow<Boolean>()
    val isWorkManager: SharedFlow<Boolean>
        get() = _isWorkManager

    // Tmap
    private val _tmapWalkRoadInfo = MutableSharedFlow<FeatureCollectionDto>()
    val tmapWalkRoadInfo: SharedFlow<FeatureCollectionDto>
        get() = _tmapWalkRoadInfo.asSharedFlow()
    
    var walkRoad: FeatureCollectionDto? = null
    
    @OptIn(ExperimentalCoroutinesApi::class)
    fun resetTmapWalkRoadInfo() {
        _tmapWalkRoadInfo.resetReplayCache()
    }

    fun requestTmapWalkRoad(
        mapLetterDto: MapLetterDto,
    ) {
        getApiResult(
            block = {
                Log.d(TAG, "requestTmapWalkRoad: Tmap 요청!!!!!!!!!!!!")
                requestTmapWalkRoadUseCase.invoke(
                    RequestTmapWalkRoadDto(
                        startX = lastLongitude.toString(),
                        startY = lastLatitude.toString(),
                        endX = mapLetterDto.longitude,
                        endY = mapLetterDto.latitude,
                        reqCoordType = "WGS84GEO", // 위경도 표현 타입 코드
                        resCoordType = "WGS84GEO",
                        startName = "내 위치",
                        endName = "편지",
                    ),
                )
            },
            success = { result ->
                Log.d(TAG, "requestTmapWalkRoad: ${result.features}")
                _tmapWalkRoadInfo.emit(result)
            },
        )
    }
    // Tmap - END

    private val _walkDistance = MutableSharedFlow<Double>()
    val walkDistance: SharedFlow<Double>
        get() = _walkDistance.asSharedFlow()

    fun calculateDistance() {
        val dist = lastLocation.distanceTo(goalLocation)
        viewModelScope.launch {
            _walkDistance.emit(dist.toDouble())
        }
    }

    // 임시 사용
    fun test() {
        getApiResult(
            block = {
                testUseCase.invoke()
            },
            success = {
                Log.d(TAG, "test: $it")
            },
        )
    }
}
