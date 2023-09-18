package com.xoka74.chess.domain.repositories

import com.xoka74.chess.domain.models.Game
import com.xoka74.chess.domain.models.PaginatedGameList

interface GameRepository {
    suspend fun getGamesHistory(page: Int) : PaginatedGameList?
    suspend fun getGameById(id: Int) : Game?
    suspend fun getPendingGame() : Game?
}