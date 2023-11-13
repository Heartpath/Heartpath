package com.zootopia.domain.model.writeletter

data class TypingLetterRequestDto (
    val receiverId: String,
    val text: String
)