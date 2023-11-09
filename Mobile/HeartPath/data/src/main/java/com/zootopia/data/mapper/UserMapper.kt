package com.zootopia.data.mapper

import com.zootopia.data.model.user.response.PointInfoResponse
import com.zootopia.data.model.user.response.UserInfoResponse
import com.zootopia.domain.model.user.PointDto
import com.zootopia.domain.model.user.UserInfoDto

fun UserInfoResponse.toDomain(): UserInfoDto? {
    return data
}

fun PointInfoResponse.toDomain(): List<PointDto>? {
    return data
}