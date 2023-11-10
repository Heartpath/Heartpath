package com.zootopia.data.repository.letter

import com.zootopia.data.datasource.remote.sendletter.unplaced.LetterDataSource
import com.zootopia.data.mapper.toDomain
import com.zootopia.domain.model.unplacedletter.UnPlacedLetterListDto
import com.zootopia.domain.repository.letter.SendLetterRepository
import com.zootopia.domain.util.getValueOrThrow2
import javax.inject.Inject

class SendLetterRepositoryImpl @Inject constructor(
    private val letterDataSource: LetterDataSource
) : SendLetterRepository {
    override suspend fun getUnplacedLetter(): UnPlacedLetterListDto {
        return getValueOrThrow2 {
            letterDataSource.getUnplacedLetter().toDomain()
        }
    }
}