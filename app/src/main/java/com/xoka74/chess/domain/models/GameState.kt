package com.xoka74.chess.domain.models

data class GameState(
    val board: String,
    val gameStatus: GameStatus,
){
    fun isWhiteNextMove() : Boolean{
        return board.split(' ')[1] == "w"
    }
}