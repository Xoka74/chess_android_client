package com.xoka74.chess.data.interceptors

import com.xoka74.chess.data.auth.UserInfoManager
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val userInfoManager: UserInfoManager,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = userInfoManager.getToken(userInfoManager.accessTokenKey)
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "token $token")
            .build()
        return chain.proceed(request)
    }
}