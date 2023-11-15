package com.zootopia.domain.model.store

import com.google.gson.annotations.SerializedName

data class ChangeMainCharacterRequestDto (
    @SerializedName("crowTitId")
    val characterId: Int
)