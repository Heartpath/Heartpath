package com.zootopia.data.repository.receiveletter

import com.zootopia.data.datasource.remote.receiveletter.ReceiveLetterDataSource
import com.zootopia.data.mapper.toDomain
import com.zootopia.domain.model.letter.ReceiveLetterDto
import com.zootopia.domain.repository.receiveletter.ReceiveLetterRepository
import com.zootopia.domain.util.getValueOrThrow2

class ReceiveLetterRepositoryImpl(
    private val receiveLetterDataSource: ReceiveLetterDataSource
): ReceiveLetterRepository {
    override suspend fun getStoredLetterList(): List<ReceiveLetterDto>? {
        return getValueOrThrow2 {
            receiveLetterDataSource.getStoredLetterList().toDomain()
        }
    }

}