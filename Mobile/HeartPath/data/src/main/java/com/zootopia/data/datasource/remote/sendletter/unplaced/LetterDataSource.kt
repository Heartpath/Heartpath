package com.zootopia.data.datasource.remote.sendletter.unplaced

import com.zootopia.data.model.letter.response.UnplacedLetterListResponse

interface LetterDataSource {
    suspend fun getUnplacedLetter(): UnplacedLetterListResponse
}