package com.xoka74.chess.data.repositories

import com.xoka74.chess.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideGameRepository(apiService: ApiService) : com.xoka74.chess.domain.repositories.GameRepository {
        return GameRepositoryImpl(apiService)
    }
}