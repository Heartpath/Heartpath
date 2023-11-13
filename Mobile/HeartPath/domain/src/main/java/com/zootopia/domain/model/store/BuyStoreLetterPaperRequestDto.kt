package com.zootopia.domain.model.store

import com.google.gson.annotations.SerializedName

data class BuyStoreLetterPaperRequestDto (
    @SerializedName("letterpaperId")
    val letterpaperId: Int
)