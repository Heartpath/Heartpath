package com.zootopia.domain.model.store

import com.google.gson.annotations.SerializedName

data class StoreItemLetterPaperDto (
    @SerializedName("letterpaperId")
    val letterpaperId: Int,
    @SerializedName("name")
    val letterName: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("imagePath")
    val imagePath: String,
    @SerializedName("owned")
    val isOwned: Boolean
)