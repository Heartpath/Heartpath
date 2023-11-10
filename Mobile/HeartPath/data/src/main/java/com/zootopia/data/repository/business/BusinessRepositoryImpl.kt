package com.zootopia.data.repository.business

import com.zootopia.data.datasource.remote.business.BusinessDataSource
import com.zootopia.data.mapper.toData
import com.zootopia.data.util.MultipartUtil
import com.zootopia.data.util.handleApi
import com.zootopia.domain.model.writeletter.HandLetterRequestDto
import com.zootopia.domain.repository.business.BusinessRepository
import com.zootopia.domain.util.getValueOrThrow2
import javax.inject.Inject

class BusinessRepositoryImpl @Inject constructor(
    private val businessDataSource: BusinessDataSource
) : BusinessRepository {
    override suspend fun postHandWriteLetter(handLetterRequestDto: HandLetterRequestDto, content: String, fileList: MutableList<String>){
        getValueOrThrow2 {
            var postHandLetterRequest = handLetterRequestDto.toData()
            var contentMultipart = MultipartUtil.createMultipartBodyPartOnePhoto(content, "content")
            var multipartFileList = fileList.map {
                MultipartUtil.createMultipartBodyPartOnePhoto(it, "files")
            }
            businessDataSource.postHandLetter(postHandLetterRequest, contentMultipart, multipartFileList)
        }
    }
}