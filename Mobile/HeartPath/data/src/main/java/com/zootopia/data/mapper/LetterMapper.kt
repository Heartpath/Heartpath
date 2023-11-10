package com.zootopia.data.mapper

import com.zootopia.data.model.letter.response.UnplacedLetterListResponse
import com.zootopia.data.model.letter.response.UnplacedLetterResponse
import com.zootopia.domain.model.unplacedletter.UnPlacedLetterListDto
import com.zootopia.domain.model.unplacedletter.UnplacedLetterDto

fun UnplacedLetterListResponse.toDomain(): UnPlacedLetterListDto {
    return UnPlacedLetterListDto(
        unPlacedLetterList = data?.map { it.toDomain() }?.toMutableList()
    )
}

fun UnplacedLetterResponse.toDomain(): UnplacedLetterDto {
    return UnplacedLetterDto(
        index = index,
        receiver = receiver
    )
}

