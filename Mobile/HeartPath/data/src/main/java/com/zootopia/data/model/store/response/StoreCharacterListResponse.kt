package com.zootopia.data.model.store.response

import com.google.gson.annotations.SerializedName
import com.zootopia.domain.model.store.StoreCharacterDto

data class StoreCharacterListResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: MutableList<StoreCharacterDto>
)