package com.xoka74.chess.domain.models

data class GameMember(
    val id : Int,
    val user: UserDisplayInfo,
    val isWinner: Boolean,
)