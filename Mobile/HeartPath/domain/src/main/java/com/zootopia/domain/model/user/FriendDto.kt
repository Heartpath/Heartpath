package com.zootopia.domain.model.user

import com.google.gson.annotations.SerializedName


data class FriendDto (
    @SerializedName("memeberId")
    val memberId: String = "iam_babsae_friend",
    @SerializedName("nickname")
    val nickname: String = "김뱁새 친구",
    @SerializedName("profileImage")
    val profileImage: String = "",
)