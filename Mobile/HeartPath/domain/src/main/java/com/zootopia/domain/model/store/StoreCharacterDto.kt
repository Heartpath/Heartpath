package com.zootopia.domain.model.store

import com.google.gson.annotations.SerializedName

data class StoreCharacterDto (
    @SerializedName("crowTitId")
    val characterId: Int,
    @SerializedName("name")
    val characterName: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("imagePath")
    val imagePath: String,
    @SerializedName("owned")
    val isOwned: Boolean
)