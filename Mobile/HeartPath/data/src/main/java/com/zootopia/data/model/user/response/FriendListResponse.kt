package com.zootopia.data.model.user.response

import com.google.gson.annotations.SerializedName
import com.zootopia.domain.model.user.FriendDto

data class FriendListResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<FriendDto>?
)