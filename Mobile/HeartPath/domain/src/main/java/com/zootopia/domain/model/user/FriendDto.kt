package com.zootopia.domain.model.user

import com.google.gson.annotations.SerializedName


data class FriendDto (
    @SerializedName("memberID")
    val memberId: String = "iam_babsae_friend",
    @SerializedName("nickname")
    val nickname: String = "김뱁새 친구",
    @SerializedName("profileImagePath")
    val profileImage: String = "",
)