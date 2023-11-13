package com.zootopia.data.datasource.remote.letter

import com.zootopia.data.model.common.MessageResponse
import com.zootopia.data.model.letter.request.LetterPlacedRequest
import com.zootopia.data.model.letter.response.UnplacedLetterListResponse
import okhttp3.MultipartBody

interface LetterDataSource {
    // 미발송 편지 목록 조회
    suspend fun getUnplacedLetter(): UnplacedLetterListResponse

    // 편지 배치
    suspend fun requestLetterPlaced(
        files: MultipartBody.Part,
        letterPlacedRequest: LetterPlacedRequest,
    ): MessageResponse
}
