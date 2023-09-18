package com.xoka74.chess.data

import com.xoka74.chess.data.auth.RefreshAuthenticator
import com.xoka74.chess.data.auth.UserInfoManager
import com.xoka74.chess.data.interceptors.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideOkHttpClient(
        authenticator: RefreshAuthenticator,
        userInfoManager: UserInfoManager
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .addInterceptor(TokenInterceptor(userInfoManager))
            .authenticator(authenticator)
            .build()
    }

    @Provides
    @Named("baseUrl")
    fun provideBaseUrl(): String {
        return "1fd8-85-140-19-223.ngrok-free.app"
    }
}