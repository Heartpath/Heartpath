package com.zootopia.data.model.letter.response

data class BusinessResponse (
    val status: String,
    val message: String,
    val data: Any?
)