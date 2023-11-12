package com.zootopia.data.datasource.remote.letter

import com.zootopia.data.model.common.MessageResponse
import com.zootopia.data.model.letter.request.LetterPlacedRequest
import com.zootopia.data.model.letter.response.UnplacedLetterListResponse
import com.zootopia.data.service.BusinessService
import com.zootopia.data.util.handleApi
import okhttp3.MultipartBody

class LetterDataSourceImpl(
    private val businessService: BusinessService,
) : LetterDataSource {
    override suspend fun getUnplacedLetter(): UnplacedLetterListResponse {
        return handleApi {
            businessService.getUnplacedLetter()
        }
    }

    override suspend fun requestLetterPlaced(
        files: MultipartBody.Part,
        letterPlacedRequest: LetterPlacedRequest,
    ): MessageResponse {
        return handleApi {
            businessService.requestLetterPlaced(
                files = files,
                letterPlacedRequest = letterPlacedRequest,
            )
        }
    }
}
