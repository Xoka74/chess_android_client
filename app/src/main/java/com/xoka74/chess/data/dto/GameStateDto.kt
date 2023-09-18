package com.xoka74.chess.data.dto

import com.google.gson.annotations.SerializedName

data class GameStateDto(
    val board: String,
    @SerializedName("game_status") val gameStatus: Int,
){
    fun toDomainModel(): com.xoka74.chess.domain.models.GameState {
        return com.xoka74.chess.domain.models.GameState(
            board = this.board,
            gameStatus = com.xoka74.chess.domain.models.GameStatus.getByValue(this.gameStatus)
                ?: com.xoka74.chess.domain.models.GameStatus.STARTED,
        )
    }
}

