package com.zootopia.data.model.store.request

import com.google.gson.annotations.SerializedName

data class ChangeMainCharacterRequest (
    @SerializedName("crowTitId")
    val characterId: Int
)