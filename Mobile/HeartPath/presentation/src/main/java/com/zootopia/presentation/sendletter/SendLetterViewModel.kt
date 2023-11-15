package com.zootopia.presentation.sendletter

import android.content.Context
import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.zootopia.domain.model.letter.sendletter.LetterPlacedDto
import com.zootopia.domain.model.letter.unplacedletter.UnPlacedLetterListDto
import com.zootopia.domain.usecase.letter.send.GetUnplacedLetterUseCase
import com.zootopia.domain.usecase.letter.send.RequestLetterPlacedUseCase
import com.zootopia.presentation.config.BaseViewModel
import com.zootopia.presentation.util.getRealPathFromUri
import com.zootopia.presentation.util.takePhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

private const val TAG = "SendLetterViewModel_HP"

@HiltViewModel
class SendLetterViewModel @Inject constructor(
    private val getUnplacedLetterUseCase: GetUnplacedLetterUseCase,
    private val requestLetterPlacedUseCase: RequestLetterPlacedUseCase,
) : BaseViewModel() {

    // user posi
    var lastLatitude: Double = 0.0
    var lastLongitude: Double = 0.0
    val lastLocation = Location("userProvider")
    fun setLocation(latitude: Double, longitude: Double) {
        lastLatitude = latitude
        lastLongitude = longitude
    }

    /**
     * 미발송 편지 얻기
     */
    private val _unplacedLetter = MutableSharedFlow<UnPlacedLetterListDto>()
    val unplacedLetter: SharedFlow<UnPlacedLetterListDto>
        get() = _unplacedLetter

    fun getUnplacedLetter() {
        getApiResult(
            block = {
                getUnplacedLetterUseCase.invoke()
            },
            success = {
                Log.d(TAG, "getUnplacedLetter: $it")
                _unplacedLetter.emit(it)
            },
        )
    }

    /**
     * 편지 전송
     */
    var isSelectLetterId = ""
    private val _isResult = MutableSharedFlow<String>()
    val isResult: SharedFlow<String> = _isResult
    fun requestSendLetter(
        files: String,
    ) {
        getApiResult(
            block = {
                requestLetterPlacedUseCase.invoke(
                    files = files,
                    letterPlacedDto = LetterPlacedDto(
                        id = isSelectLetterId,
                        lat = lastLatitude,
                        lng = lastLongitude,
                    ),
                )
            },
            success = {
                Log.d(TAG, "requestSendLetter: $it")
                _isResult.emit(it)
            },
        )
    }

    /**
     * 화면 캡처 전처리
     */
    private val _isSaveImage = MutableSharedFlow<Uri>()
    val isSaveIamge: SharedFlow<Uri> = _isSaveImage
    private val _isRealPath = MutableSharedFlow<String>()
    val isRealPath: SharedFlow<String> = _isRealPath

    suspend fun catchCapture(fragment: Fragment) {
        val photoUriDeferred = takePhoto(fragment = fragment)
        val photoUri = photoUriDeferred.await()

        photoUri?.let {
            Log.d(TAG, "catchCapture: $it")
            _isSaveImage.emit(
                it,
            )
        }
    }

    suspend fun getRealPath(context: Context, uri: Uri) {
        getRealPathFromUri(context = context, contentUri = uri)?.let {
            _isRealPath.emit(it)
        }
    }
}
