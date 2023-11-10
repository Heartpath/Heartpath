package com.zootopia.domain.model.user


import com.google.gson.annotations.SerializedName

data class UserInfoDto(
    @SerializedName("characterImagePath")
    val characterImagePath: String = "https://zootopia-s3.s3.ap-northeast-2.amazonaws.com/crowtit/98bb8f06-4233-4678-9ebd-50ae629cf780.%EA%B3%B5%ED%95%99_%EB%B1%81%EC%83%88.png",
    @SerializedName("memberID")
    val memberID: String = "@iam_babsae",
    @SerializedName("nickname")
    val nickname: String = "김뱁새",
    @SerializedName("point")
    val point: Int = 0,
    @SerializedName("profileImagePath")
    val profileImagePath: String = "https://zootopia-s3.s3.ap-northeast-2.amazonaws.com/crowtit/98bb8f06-4233-4678-9ebd-50ae629cf780.%EA%B3%B5%ED%95%99_%EB%B1%81%EC%83%88.png",
)