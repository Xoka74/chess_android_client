package com.xoka74.chess.data.dto

import com.google.gson.annotations.SerializedName

data class EndGameDto(
    val board: String,
    @SerializedName("game_status") val gameStatus: Int,
    @SerializedName("is_white_winner") val isWhiteWinner: Boolean,
)