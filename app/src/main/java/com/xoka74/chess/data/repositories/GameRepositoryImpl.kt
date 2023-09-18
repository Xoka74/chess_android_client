package com.xoka74.chess.data.repositories

import com.xoka74.chess.data.api.ApiService
import com.xoka74.chess.data.dto.toDomainModel

class GameRepositoryImpl(
    private val apiService: ApiService,
) : com.xoka74.chess.domain.repositories.GameRepository {

    override suspend fun getGamesHistory(page: Int): com.xoka74.chess.domain.models.PaginatedGameList? {
        return apiService.getGamesHistory(page).body()?.toDomainModel()
    }

    override suspend fun getGameById(id: Int): com.xoka74.chess.domain.models.Game? {
        return apiService.getGameById(id).body()?.toDomainModel()
    }

    override suspend fun getPendingGame(): com.xoka74.chess.domain.models.Game? {
        return apiService.getPendingGame().body()?.toDomainModel()
    }
}