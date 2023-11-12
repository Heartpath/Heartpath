package com.zootopia.data.datasource.remote.business

import com.zootopia.data.model.letter.request.PostHandLetterRequest
import com.zootopia.data.model.letter.request.PostTypingLetterRequest
import com.zootopia.data.model.letter.response.BusinessResponse
import com.zootopia.data.model.letter.response.GetUserLetterPaperResponse
import okhttp3.MultipartBody

interface BusinessDataSource {
    suspend fun postHandLetter(
        postHandLetterRequest: PostHandLetterRequest,
        content: MultipartBody.Part,
        files: List<MultipartBody.Part>
    ): BusinessResponse

    suspend fun postTypingLetter(
        postTypingLetterRequest: PostTypingLetterRequest,
        content: MultipartBody.Part,
        files: List<MultipartBody.Part>
    ): BusinessResponse


    suspend fun getUserLetterPaper(): GetUserLetterPaperResponse
}