package com.xoka74.chess.data.api

import com.xoka74.chess.data.dto.GameDto
import com.xoka74.chess.data.dto.PaginatedGameListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/session/")
    suspend fun getPendingGame() : Response<GameDto>

    @GET("api/history/")
    suspend fun getGamesHistory(@Query("page") page: Int) : Response<PaginatedGameListDto>

    @GET("api/session/{id}")
    suspend fun getGameById(@Path("id") id: Int) : Response<GameDto>
}