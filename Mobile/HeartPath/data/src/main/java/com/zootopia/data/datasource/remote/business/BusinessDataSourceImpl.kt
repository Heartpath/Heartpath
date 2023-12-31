package com.zootopia.data.datasource.remote.business

import com.zootopia.data.model.letter.request.PostHandLetterRequest
import com.zootopia.data.model.letter.request.PostTypingLetterRequest
import com.zootopia.data.model.letter.response.BusinessResponse
import com.zootopia.data.model.letter.response.GetUserLetterPaperResponse
import com.zootopia.data.service.BusinessService
import com.zootopia.data.util.handleApi
import okhttp3.MultipartBody

class BusinessDataSourceImpl(
    private val businessService: BusinessService,
) : BusinessDataSource {

    override suspend fun postHandLetter(
        postHandLetterRequest: PostHandLetterRequest,
        content: MultipartBody.Part,
        files: List<MultipartBody.Part>
    ): BusinessResponse {
        return handleApi {
            businessService.postHandLetter(postHandLetterRequest, content, files)
        }
    }

    override suspend fun postTypingLetter(
        postTypingLetterRequest: PostTypingLetterRequest,
        content: MultipartBody.Part,
        files: List<MultipartBody.Part>
    ): BusinessResponse {
        return handleApi {
            businessService.postTypingLetter(postTypingLetterRequest, content, files)
        }
    }


    override suspend fun getUserLetterPaper(): GetUserLetterPaperResponse {
        return handleApi {
            businessService.getUserLetterPaper()
        }
    }

}