package com.xoka74.chess.data.websockets

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.crossbar.autobahn.websocket.WebSocketConnection
import io.crossbar.autobahn.websocket.types.WebSocketOptions
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class WebSocketModule {

    @Provides
    fun provideWebSocketConnection(): WebSocketConnection {
        val connection = WebSocketConnection()
        val options = WebSocketOptions()
        connection.setOptions(options)
        return connection
    }

    @Named("gameUrl")
    @Provides
    fun provideGameUrl(@Named("baseUrl") baseUrl: String): String {

        return "wss://$baseUrl/ws/sessions/"
    }

    @Named("matchMakingURI")
    @Provides
    fun provideMatchMakingURI(@Named("baseUrl") baseUrl: String): String {
        return "wss://$baseUrl/ws/matchmaking/"
    }
}