package com.zootopia.data.model.store.response

import com.google.gson.annotations.SerializedName

data class ChangeMainCharacterResponse (
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
)