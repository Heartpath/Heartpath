package com.zootopia.domain.model.letter

import com.google.gson.annotations.SerializedName

data class UserLetterPaperDto (
    @SerializedName("index")
    val index: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("imagePath")
    val imagePath: String,
    @SerializedName("isowned")
    val isOwend: Boolean
)