package com.xoka74.chess.data.auth

import com.xoka74.chess.data.auth.requests.CreateUserRequest
import com.xoka74.chess.data.auth.responses.CreateUserResponse
import com.xoka74.chess.data.auth.responses.LoginResponse
import com.xoka74.chess.data.auth.responses.RefreshResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {

    @FormUrlEncoded
    @POST("auth/jwt/create")
    suspend fun login(@Field("username") username: String, @Field("password") password: String): Response<LoginResponse>

    @FormUrlEncoded
    @POST("auth/jwt/refresh")
    suspend fun refresh(@Field("refresh") refreshToken: String?): Response<RefreshResponse>

    @FormUrlEncoded
    @POST("auth/jwt/verify")
    suspend fun verify(@Field("token") token: String?) : Response<Void>

    @FormUrlEncoded
    @POST("auth/users")
    suspend fun createUser(@Body createUserRequest: CreateUserRequest) : Response<CreateUserResponse>
}