package com.zootopia.data.model.letter.response

import com.google.gson.annotations.SerializedName

data class UnplacedLetterResponse(
    @SerializedName("index")
    val index: String,
    @SerializedName("receiver")
    val receiver: String
)

