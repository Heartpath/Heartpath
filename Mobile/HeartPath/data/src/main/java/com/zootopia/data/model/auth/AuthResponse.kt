package com.zootopia.data.model.auth

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: String,
)