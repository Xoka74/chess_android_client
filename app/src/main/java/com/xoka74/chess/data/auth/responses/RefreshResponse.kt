package com.xoka74.chess.data.auth.responses

import com.google.gson.annotations.SerializedName

data class RefreshResponse(
    @SerializedName("access") val accessToken: String,
)