package com.zootopia.data.repository.letter

import com.zootopia.data.datasource.remote.letter.LetterDataSource
import com.zootopia.data.mapper.toData
import com.zootopia.data.mapper.toDomain
import com.zootopia.domain.model.letter.sendletter.LetterPlacedDto
import com.zootopia.domain.model.letter.unplacedletter.UnPlacedLetterListDto
import com.zootopia.domain.repository.letter.SendLetterRepository
import com.zootopia.domain.util.getValueOrThrow2
import okhttp3.MultipartBody
import javax.inject.Inject

class SendLetterRepositoryImpl @Inject constructor(
    private val letterDataSource: LetterDataSource,
) : SendLetterRepository {
    override suspend fun getUnplacedLetter(): UnPlacedLetterListDto {
        return getValueOrThrow2 {
            letterDataSource.getUnplacedLetter().toDomain()
        }
    }

    override suspend fun requestLetterPlaced(
        files: MultipartBody.Part,
        letterPlacedDto: LetterPlacedDto,
    ): String {
        return getValueOrThrow2 {
            letterDataSource.requestLetterPlaced(
                files = files,
                letterPlacedRequest = letterPlacedDto.toData(),
            )
        }.toDomain()
    }
}
