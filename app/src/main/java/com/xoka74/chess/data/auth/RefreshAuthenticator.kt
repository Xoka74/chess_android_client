package com.xoka74.chess.data.auth

import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class RefreshAuthenticator(
    private val userInfoManager: UserInfoManager,
    private val authService: AuthService,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = userInfoManager.getToken(userInfoManager.refreshTokenKey)
        val newAccessToken = runBlocking {
            authService.refresh(refreshToken)
        }

        if (!newAccessToken.isSuccessful || newAccessToken.body() == null) {
            userInfoManager.deleteToken(userInfoManager.refreshTokenKey)
        }

        return newAccessToken.body()?.let {
            userInfoManager.saveToken(userInfoManager.accessTokenKey, it.accessToken)
            response.request.newBuilder()
                .header("Authorization", "token ${it.accessToken}")
                .build()
        }
    }
}