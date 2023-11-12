package com.zootopia.data.model.store.response

import com.google.gson.annotations.SerializedName

data class BuyStoreLetterPaperResponse (
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String
)