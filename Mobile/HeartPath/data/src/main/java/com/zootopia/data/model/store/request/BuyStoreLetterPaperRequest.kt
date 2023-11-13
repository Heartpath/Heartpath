package com.zootopia.data.model.store.request

import com.google.gson.annotations.SerializedName

data class BuyStoreLetterPaperRequest (
    @SerializedName("letterpaperId")
    val letterpaperId: Int
)