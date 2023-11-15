package com.zootopia.domain.model.letter

import com.google.gson.annotations.SerializedName

data class ReadLetterDto(
    @SerializedName("content")
    val content: String = "",
    @SerializedName("files")
    val files: List<String> = mutableListOf(),
    @SerializedName("friend")
    val friend: Boolean = true,
    @SerializedName("index")
    val index: Int = 0,
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lng")
    val lng: Double = 0.0,
    @SerializedName("receiver")
    val receiver: String = "",
    @SerializedName("sender")
    val sender: String = "",
    @SerializedName("senderID")
    val senderId: String = "",
    @SerializedName("time")
    val time: String = "",
)