package com.zootopia.domain.model.user

import com.google.gson.annotations.SerializedName


data class FriendDto (
    @SerializedName("memeberId")
    val memberId: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("profileImage")
    val profileImage: String,
)