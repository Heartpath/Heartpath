package com.zootopia.data.datasource.remote.receiveletter

import com.zootopia.data.model.letter.response.StoredLetterListResponse
import com.zootopia.data.service.BusinessService
import com.zootopia.data.util.handleApi

class ReceiveLetterDataSourceImpl(
    private val businessService: BusinessService
): ReceiveLetterDataSource {
    override suspend fun getStoredLetterList(): StoredLetterListResponse {
        return handleApi { businessService.getStoredLetterList() }
    }
}