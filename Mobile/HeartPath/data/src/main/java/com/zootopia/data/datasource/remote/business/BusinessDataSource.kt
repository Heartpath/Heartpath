package com.zootopia.data.datasource.remote.business

import com.zootopia.data.model.business.request.PostHandLetterRequest
import com.zootopia.data.model.business.response.BusinessResponse
import okhttp3.MultipartBody

interface BusinessDataSource {
    suspend fun postHandLetter(
        postHandLetterRequest: PostHandLetterRequest,
        content: MultipartBody.Part,
        files: List<MultipartBody.Part>
    ): BusinessResponse
}