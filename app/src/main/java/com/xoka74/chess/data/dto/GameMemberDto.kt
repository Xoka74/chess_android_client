package com.xoka74.chess.data.dto

import com.google.gson.annotations.SerializedName
import com.xoka74.chess.domain.models.GameMember

data class GameMemberDto(
    val id: Int,
    val user: UserDisplayInfoDto,
    @SerializedName("is_winner") val isWinner: Boolean,
) {
    fun toDomainModel(): GameMember {
        return GameMember(
            id = id,
            user = user.toDomainModel(),
            isWinner = isWinner,
        )
    }
}


