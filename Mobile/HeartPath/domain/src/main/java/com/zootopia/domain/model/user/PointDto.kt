package com.zootopia.domain.model.user

import com.google.gson.annotations.SerializedName

data class PointDto (
    @SerializedName("outline")
    val outline: String = "포인트 정보",
    @SerializedName("price")
    val price: Int = 0,
    @SerializedName("balance")
    val balance: Int = 1000,
    @SerializedName("date")
    val date: String = "2023.10.03"
)