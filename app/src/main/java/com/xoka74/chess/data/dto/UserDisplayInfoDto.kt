package com.xoka74.chess.data.dto

import com.xoka74.chess.domain.models.UserDisplayInfo

data class UserDisplayInfoDto(
    val id: Int,
    val username: String,
) {
    fun toDomainModel(): UserDisplayInfo {
        return UserDisplayInfo(
            username = username,
            id = id,
        )
    }
}


