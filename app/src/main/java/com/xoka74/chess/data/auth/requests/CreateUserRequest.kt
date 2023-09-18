package com.xoka74.chess.data.auth.requests

import com.google.gson.annotations.SerializedName

data class CreateUserRequest(
    val username: String,
    val email: String,
    val password: String,
    @SerializedName("re_password") val rePassword: String,
)