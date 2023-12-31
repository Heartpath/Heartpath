package com.zootopia.presentation.map

import android.location.Location
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zootopia.domain.model.letter.uncheckedletter.UncheckLetterDto
import com.zootopia.domain.model.tmap.FeatureCollectionDto
import com.zootopia.domain.model.tmap.RequestTmapWalkRoadDto
import com.zootopia.domain.usecase.map.GetMapDirectionUseCase
import com.zootopia.domain.usecase.map.GetPickUpLetterUseCase
import com.zootopia.domain.usecase.map.GetUncheckedLetterUseCase
import com.zootopia.domain.usecase.map.RequestTmapWalkRoadUseCase
import com.zootopia.domain.usecase.store.PostPointUseCase
import com.zootopia.domain.usecase.user.PutOpponentFriendUseCase
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
    private val uncheckedLetterUseCase: GetUncheckedLetterUseCase,
    private val getPickUpLetterUseCase: GetPickUpLetterUseCase,
    private val putOpponentFriendUseCase: PutOpponentFriendUseCase,
    private val postPointUseCase: PostPointUseCase,
) : BaseViewModel() {

    // 편지 신고 삭제 신고 클릭 유무
    var isReport = false

    // 미확인 편지 리스트
    var uncheckedLetterList: MutableList<UncheckLetterDto> = mutableListOf()

    private val _mapLetterList = MutableSharedFlow<List<UncheckLetterDto>>()
    val mapLetterList: SharedFlow<List<UncheckLetterDto>>
        get() = _mapLetterList.asSharedFlow()

    fun getUncheckedLetterList() {
        getApiResult(
            block = {
                uncheckedLetterUseCase.invoke()
            },
            success = {
                uncheckedLetterList = it.toMutableList()
                _mapLetterList.emit(it)
            },
        )
    }
    // 미확인 편지 리스트 END

    // select Letter
    var selectLetter: UncheckLetterDto? = null

    // user posi
    var lastLatitude: Double = 0.0
    var lastLongitude: Double = 0.0
    fun setLocation(latitude: Double, longitude: Double) {
        lastLatitude = latitude
        lastLongitude = longitude
    }

    // user: Location
    val lastLocation = Location("userProvider")
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

    // Tmap
    private val _tmapWalkRoadInfo = MutableSharedFlow<FeatureCollectionDto>()
    val tmapWalkRoadInfo: SharedFlow<FeatureCollectionDto>
        get() = _tmapWalkRoadInfo.asSharedFlow()

    var walkRoad: FeatureCollectionDto? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    fun resetTmapWalkRoadInfo() {
        _tmapWalkRoadInfo.resetReplayCache()
        walkRoad = null
    }

    fun requestTmapWalkRoad(
        uncheckLetterDto: UncheckLetterDto,
    ) {
        getApiResult(
            block = {
                Log.d(TAG, "requestTmapWalkRoad: Tmap 요청!!!!!!!!!!!!")
                requestTmapWalkRoadUseCase.invoke(
                    RequestTmapWalkRoadDto(
                        startX = lastLongitude.toString(),
                        startY = lastLatitude.toString(),
                        endX = uncheckLetterDto.lng.toString(),
                        endY = uncheckLetterDto.lat.toString(),
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

    /**
     * 편지 pick up API
     */
    private val _isPickUpLetter = MutableSharedFlow<String>()
    val isPickUpLetter: SharedFlow<String> = _isPickUpLetter.asSharedFlow()

    fun getPickUpLetter(letter_id: Int) {
        getApiResult(
            block = {
                getPickUpLetterUseCase.invoke(letter_id = letter_id)
            },
            success = {
                Log.d(TAG, "getPickUpLetter: $it")
                isStartWalk = false
                _isPickUpLetter.emit(it)
            },
        )
    }
    
    /**
     * 친구차단
     */
    private val _isOpponentFriend = MutableSharedFlow<String>()
    val isOppenentFriend: SharedFlow<String> = _isOpponentFriend.asSharedFlow()
    
    fun putOpponentFriend(opponentID: String) {
        getApiResult(
            block = {
                putOpponentFriendUseCase.invoke(opponentID = opponentID)
            },
            success = {
                _isOpponentFriend.emit(it)
            }
        )
    }
    
    // 포인트 적립
    private val _isPostPoint = MutableSharedFlow<String>()
    val isPostPoint: SharedFlow<String> = _isPostPoint.asSharedFlow()
    
    fun postPoint() {
        getApiResult(
            block = {
                // todo 포인트 저장 위치 만들어서 사용??
                postPointUseCase.invoke(point = 100)
            },
            success = {
                Log.d(TAG, "postPoint: 포인트 적립 성공")
                _isPostPoint.emit(it)
            }
        )
    }
}
