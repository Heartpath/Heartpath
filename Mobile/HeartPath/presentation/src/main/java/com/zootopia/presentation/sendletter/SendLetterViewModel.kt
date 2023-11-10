package com.zootopia.presentation.sendletter

import android.location.Location
import android.util.Log
import com.zootopia.domain.model.unplacedletter.UnPlacedLetterListDto
import com.zootopia.domain.usecase.letter.send.GetUnplacedLetterUseCase
import com.zootopia.presentation.config.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject


private const val TAG = "SendLetterViewModel_HP"

@HiltViewModel
class SendLetterViewModel @Inject constructor(
    private val getUnplacedLetterUseCase: GetUnplacedLetterUseCase
): BaseViewModel() {
    
    // user posi
    var lastLatitude: Double = 0.0
    var lastLongitude: Double = 0.0
    val lastLocation = Location("userProvider")
    fun setLocation(latitude: Double, longitude: Double) {
        lastLatitude = latitude
        lastLongitude = longitude
    }
    
    private val _unplacedLetter = MutableSharedFlow<UnPlacedLetterListDto>()
    val unplacedLetter: SharedFlow<UnPlacedLetterListDto>
        get() = _unplacedLetter
    
    /**
     * 미발송 편지 얻기
     */
    fun getUnplacedLetter() {
        getApiResult(
            block = {
                getUnplacedLetterUseCase.invoke()
            },
            success = {
                Log.d(TAG, "getUnplacedLetter: $it")
                _unplacedLetter.emit(it)
            }
        )
    }
    
    /**
     * 편지 전송
     */
    fun requestSendLetter() {
        getApiResult(
            block = {
            
            },
            success = {
            
            }
        )
    }
}
