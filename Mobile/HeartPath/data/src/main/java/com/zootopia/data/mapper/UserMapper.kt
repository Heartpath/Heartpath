package com.zootopia.data.mapper

import com.zootopia.data.model.user.response.UserInfoResponse
import com.zootopia.domain.model.user.UserInfoDto

fun UserInfoResponse.toDomain(): UserInfoDto {
    return UserInfoDto(
        return data
    )
}