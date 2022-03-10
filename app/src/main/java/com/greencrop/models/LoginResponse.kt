package com.chatbot.models

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("status")
    val status: String? = null,
)