package com.zootopia.domain.model.user

import com.google.gson.annotations.SerializedName

data class SearchUserInfoDto (
    @SerializedName("memberID")
    val memberID: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("profileImagePath")
    val profileImagePath: String,
    @SerializedName("isFriend")
    val isFriend: Boolean
)