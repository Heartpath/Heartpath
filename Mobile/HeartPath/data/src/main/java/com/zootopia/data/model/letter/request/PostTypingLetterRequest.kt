package com.zootopia.data.model.letter.request

data class PostTypingLetterRequest (
    val receiverId: String,
    val text: String
)