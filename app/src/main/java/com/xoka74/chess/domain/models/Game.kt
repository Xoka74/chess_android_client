package com.xoka74.chess.domain.models

import java.time.LocalDateTime

data class Game(
    val id : Int,
    val startDateTime : LocalDateTime,
    val endDateTime: LocalDateTime?,
    val board: String,
    val status: GameStatus,
    val white: GameMember,
    val black: GameMember,
){

}