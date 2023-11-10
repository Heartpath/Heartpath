package com.zootopia.data.model.letter.response

import com.google.gson.annotations.SerializedName

data class UnplacedLetterListResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<UnplacedLetterResponse>?
)
