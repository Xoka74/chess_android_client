package com.xoka74.chess.data.dto

import com.google.gson.annotations.SerializedName

data class JoinGameDto(
    val board: String,
    @SerializedName("game_status") val gameStatus: Int,
    @SerializedName("is_member_white") val isMemberWhite: Boolean,
)