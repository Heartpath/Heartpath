package com.zootopia.data.model.letter.response

import com.google.gson.annotations.SerializedName
import com.zootopia.domain.model.letter.ReadLetterDto
import java.io.Serial

data class ReceivedLetterDetailResponse (
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: ReadLetterDto?

)