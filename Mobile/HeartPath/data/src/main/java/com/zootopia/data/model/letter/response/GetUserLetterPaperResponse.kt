package com.zootopia.data.model.letter.response

import com.google.gson.annotations.SerializedName
import com.zootopia.domain.model.user.UserInfoDto

data class GetUserLetterPaperResponse (
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: UserInfoDto?,
)