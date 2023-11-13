package com.zootopia.data.model.letter.response

import com.google.gson.annotations.SerializedName
import com.zootopia.domain.model.letter.uncheckedletter.UncheckLetterDto

data class UncheckedLetterResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<UncheckLetterDto>?
)
