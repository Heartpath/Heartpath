package com.zootopia.data.model.user.response

import com.google.gson.annotations.SerializedName
import com.zootopia.domain.model.user.SearchUserInfoDto

data class SearchUserResponse (
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<SearchUserInfoDto>
)