package com.xoka74.chess.ui.chess.matchmaking

import android.util.Log
import androidx.lifecycle.ViewModel
import com.xoka74.chess.data.websockets.WebSocketHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import io.crossbar.autobahn.websocket.WebSocketConnection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MatchmakingViewModel @Inject constructor(
    private val wsConnection: WebSocketConnection,
    @Named("matchMakingURI") url: String,
) : ViewModel() {

    private val mutableUiState = MutableStateFlow(MatchMakingUiState())
    val uiState = mutableUiState.asStateFlow()

    init {
        Log.i("MatchmakingViewModel", "Starting...")
        val wsHandler = WebSocketHandler(onMessage = { event ->
            when (event["type"]) {
                "match_found" -> {
                    mutableUiState.update {
                        it.copy(matchId = (event["result_data"] as JSONObject)["session_id"] as Int)
                    }
                }
            }
        }, onConnectCallback = {

        }, onCloseCallback = { code, reason ->

        }, onOpenCallback = {

        })
        val finalUrl = "$url?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjk1MTE0NDc2LCJpYXQiOjE2OTQ4OTg0NzYsImp0aSI6ImEyZTg0M2YwYzY5MjQ4ZmNiZGI4NTU3MjZlZGYyMzZhIiwidXNlcl9pZCI6MX0.04PE8eyDRYByouvis6WSXQMpQMlFnFVq9wGpLpi43jI"
        Log.i("MatchMakingUrl", finalUrl)
        wsConnection.connect(finalUrl, wsHandler,)
    }

    fun reconnect() {
        wsConnection.reconnect()
    }

    override fun onCleared() {
        wsConnection.sendClose()
        super.onCleared()
    }

    data class MatchMakingUiState(
        val matchId: Int? = null,
        val isConnected: Boolean = false,
    )
}
