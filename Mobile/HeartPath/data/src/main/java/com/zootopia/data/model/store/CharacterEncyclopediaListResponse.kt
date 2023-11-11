package com.zootopia.data.model.store

import com.google.gson.annotations.SerializedName
import com.zootopia.domain.model.store.CharacterDto

data class CharacterEncyclopediaListResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: MutableList<CharacterDto>
)