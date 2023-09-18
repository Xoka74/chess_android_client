package com.xoka74.chess.domain.models

data class PaginatedGameList(
    val count : Int,
    val next: String?,
    val previous: String?,
    val results: List<Game>
)