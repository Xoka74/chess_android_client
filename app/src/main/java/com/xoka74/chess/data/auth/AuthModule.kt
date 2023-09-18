package com.xoka74.chess.data.auth

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    fun provideAuthenticator(
        tokenProvider: UserInfoManager,
        authService: AuthService
    ): RefreshAuthenticator {
        return RefreshAuthenticator(tokenProvider, authService)
    }

    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): UserInfoManager {
        return UserInfoManager(context)
    }

    @Provides
    fun provideAuthService(@Named("baseUrl") baseUrl: String): AuthService {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://$baseUrl").build()
        return retrofit.create(AuthService::class.java)
    }
}