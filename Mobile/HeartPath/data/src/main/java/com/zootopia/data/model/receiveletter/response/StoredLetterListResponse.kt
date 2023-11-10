package com.zootopia.data.model.letter.response

import com.google.gson.annotations.SerializedName
import com.zootopia.domain.model.letter.ReceiveLetterDto

data class StoredLetterListResponse (
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<ReceiveLetterDto>?,
)