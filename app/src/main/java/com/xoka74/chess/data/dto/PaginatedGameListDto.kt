package com.xoka74.chess.data.dto

data class PaginatedGameListDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<GameDto>
) {
    fun toDomainModel(): com.xoka74.chess.domain.models.PaginatedGameList {
        return com.xoka74.chess.domain.models.PaginatedGameList(
            count = count,
            next = next,
            previous = previous,
            results = results.map { it.toDomainModel() }
        )
    }
}


