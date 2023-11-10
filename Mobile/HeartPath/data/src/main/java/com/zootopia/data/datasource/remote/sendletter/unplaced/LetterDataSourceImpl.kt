package com.zootopia.data.datasource.remote.sendletter.unplaced

import com.zootopia.data.model.letter.response.UnplacedLetterListResponse
import com.zootopia.data.service.BusinessService
import com.zootopia.data.util.handleApi

class LetterDataSourceImpl (
    private val businessService: BusinessService
): LetterDataSource{
    override suspend fun getUnplacedLetter(): UnplacedLetterListResponse {
        return handleApi {
            businessService.getUnplacedLetter()
        }
    }
    
}