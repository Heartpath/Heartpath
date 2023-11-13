package com.zootopia.data.repository.letter

import android.util.Log
import com.zootopia.data.datasource.remote.letter.LetterDataSource
import com.zootopia.data.mapper.toData
import com.zootopia.data.mapper.toDomain
import com.zootopia.data.util.MultipartUtil
import com.zootopia.domain.model.letter.sendletter.LetterPlacedDto
import com.zootopia.domain.model.letter.unplacedletter.UnPlacedLetterListDto
import com.zootopia.domain.repository.letter.SendLetterRepository
import com.zootopia.domain.util.getValueOrThrow2
import javax.inject.Inject

private const val TAG = "SendLetterRepositoryImp_HP"
class SendLetterRepositoryImpl @Inject constructor(
    private val letterDataSource: LetterDataSource,
) : SendLetterRepository {
    override suspend fun getUnplacedLetter(): UnPlacedLetterListDto {
        return getValueOrThrow2 {
            letterDataSource.getUnplacedLetter().toDomain()
        }
    }

    override suspend fun requestLetterPlaced(
        files: String,
        letterPlacedDto: LetterPlacedDto,
    ): String {
        return getValueOrThrow2 {
            letterDataSource.requestLetterPlaced(
                files = MultipartUtil.createMultipartBodyPartOnePhoto(files, "files"),
                letterPlacedRequest = letterPlacedDto.toData(),
            ).message
        }
    }
}
