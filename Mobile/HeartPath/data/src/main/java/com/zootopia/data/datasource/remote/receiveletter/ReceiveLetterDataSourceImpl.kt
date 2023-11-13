package com.zootopia.data.datasource.remote.receiveletter

import com.zootopia.data.model.common.MessageResponse
import com.zootopia.data.model.letter.response.ReceivedLetterDetailResponse
import com.zootopia.data.model.letter.response.StoredLetterListResponse
import com.zootopia.data.service.BusinessService
import com.zootopia.data.util.handleApi

class ReceiveLetterDataSourceImpl(
    private val businessService: BusinessService
): ReceiveLetterDataSource {
    override suspend fun getStoredLetterList(): StoredLetterListResponse {
        return handleApi { businessService.getStoredLetterList() }
    }

    override suspend fun getLetterToRead(id: Int): ReceivedLetterDetailResponse {
        return handleApi { businessService.getLetter(letterId = id) }
    }

    override suspend fun testFCM(): MessageResponse {
        return handleApi { businessService.testFCM() }
    }
}