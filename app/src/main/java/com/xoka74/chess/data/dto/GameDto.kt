package com.xoka74.chess.data.dto

import com.google.gson.annotations.SerializedName
import com.xoka74.chess.domain.models.Game
import java.time.OffsetDateTime

data class GameDto(
    val id: Int,
    @SerializedName("start_datetime") val startDateTime: String,
    @SerializedName("end_datetime") val endDateTime: String?,
    val board: String,
    val status: Int,
    val white: GameMemberDto,
    val black: GameMemberDto,
) {
    fun toDomainModel(): Game {
        return Game(
            id = id,
            startDateTime = OffsetDateTime.parse(startDateTime).toLocalDateTime(),
            endDateTime = if (endDateTime == null) null else OffsetDateTime.parse(endDateTime)
                .toLocalDateTime(),
            board = board,
            status = com.xoka74.chess.domain.models.GameStatus.getByValue(status)
                ?: com.xoka74.chess.domain.models.GameStatus.STARTED,
            white = white.toDomainModel(),
            black = black.toDomainModel(),
        )
    }
}


