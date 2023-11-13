package com.zootopia.data.datasource.remote.receiveletter

import com.zootopia.data.model.common.MessageResponse
import com.zootopia.data.model.letter.response.ReceivedLetterDetailResponse
import com.zootopia.data.model.letter.response.StoredLetterListResponse

interface ReceiveLetterDataSource {
    suspend fun getStoredLetterList(): StoredLetterListResponse
    suspend fun getLetterToRead(id: Int): ReceivedLetterDetailResponse
    suspend fun testFCM() : MessageResponse
}