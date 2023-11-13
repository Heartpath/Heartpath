package com.zootopia.domain.model.store

import com.google.gson.annotations.SerializedName

data class BuyStoreCharacterRequestDto (
    @SerializedName("crowTitId")
    val characterId: Int
)