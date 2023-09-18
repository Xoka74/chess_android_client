package com.xoka74.chess.domain.models

enum class GameStatus(val value: Int) {
    STARTED(0),
    CHECK(1),
    CHECKMATE(2),
    STALEMATE(3),
    SURRENDER(4);

    companion object {
        fun getByValue(value: Int) = GameStatus.values().firstOrNull { it.value == value }
    }
}