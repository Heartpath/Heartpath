package com.zootopia.data.repository.business

import com.zootopia.data.datasource.remote.business.BusinessDataSource
import com.zootopia.data.mapper.toData
import com.zootopia.data.mapper.toDomain
import com.zootopia.data.util.MultipartUtil
import com.zootopia.domain.model.letter.UserLetterPaperDto
import com.zootopia.domain.model.writeletter.HandLetterRequestDto
import com.zootopia.domain.model.writeletter.TypingLetterRequestDto
import com.zootopia.domain.repository.letter.WriteLetterRepository
import com.zootopia.domain.util.getValueOrThrow2
import javax.inject.Inject

class WriteLetterRepositoryImpl @Inject constructor(
    private val businessDataSource: BusinessDataSource
) : WriteLetterRepository {
    override suspend fun postHandWriteLetter(
        handLetterRequestDto: HandLetterRequestDto,
        content: String,
        fileList: MutableList<String>
    ) {
        getValueOrThrow2 {
            var postHandLetterRequest = handLetterRequestDto.toData()
            var contentMultipart = MultipartUtil.createMultipartBodyPartOnePhoto(content, "content")
            var multipartFileList = fileList.map {
                MultipartUtil.createMultipartBodyPartOnePhoto(it, "files")
            }
            businessDataSource.postHandLetter(
                postHandLetterRequest,
                contentMultipart,
                multipartFileList
            )
        }
    }

    override suspend fun postTypingWriteLetter(
        typingLetterRequestDto: TypingLetterRequestDto,
        content: String,
        fileList: MutableList<String>
    ) {
        getValueOrThrow2 {
            var postTypingLetterRequest = typingLetterRequestDto.toData()
            var contentMultipart = MultipartUtil.createMultipartBodyPartOnePhoto(content, "content")
            var multipartFileList = fileList.map {
                MultipartUtil.createMultipartBodyPartOnePhoto(it, "files")
            }
            businessDataSource.postTypingLetter(
                postTypingLetterRequest,
                contentMultipart,
                multipartFileList
            )
        }
    }

    override suspend fun getUserLetterPaper(): MutableList<UserLetterPaperDto> {
        return getValueOrThrow2 {
            businessDataSource.getUserLetterPaper().toDomain()
        }
    }
}