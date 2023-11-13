package com.zootopia.data.datasource.remote.letter

import android.util.Log
import com.zootopia.data.model.common.MessageResponse
import com.zootopia.data.model.letter.request.LetterPlacedRequest
import com.zootopia.data.model.letter.response.UnplacedLetterListResponse
import com.zootopia.data.service.BusinessService
import com.zootopia.data.util.handleApi
import okhttp3.MultipartBody

private const val TAG = "LetterDataSourceImpl_HP"
class LetterDataSourceImpl(
    private val businessService: BusinessService,
) : LetterDataSource {
    // 미발송 편지 목록 조회
    override suspend fun getUnplacedLetter(): UnplacedLetterListResponse {
        return handleApi {
            businessService.getUnplacedLetter()
        }
    }
    
    // 편지 배치
    override suspend fun requestLetterPlaced(
        files: MultipartBody.Part,
        letterPlacedRequest: LetterPlacedRequest,
    ): MessageResponse {
        Log.d(TAG, "requestLetterPlaced: $files // $letterPlacedRequest")
        return handleApi {
            businessService.requestLetterPlaced(
                files = files,
                letterPlacedRequest = letterPlacedRequest,
            )
        }
    }
}
