package com.xoka74.chess.data.auth.responses

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access") val accessToken: String,
    @SerializedName("refresh") val refreshToken: String,
)