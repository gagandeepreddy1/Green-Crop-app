package com.chatbot.models

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("email")
    val email: String? = null,
)