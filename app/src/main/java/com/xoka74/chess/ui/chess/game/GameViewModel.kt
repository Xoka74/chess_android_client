package com.xoka74.chess.ui.chess.game

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.xoka74.chess.data.dto.EndGameDto
import com.xoka74.chess.data.dto.GameDto
import com.xoka74.chess.data.websockets.WebSocketHandler
import com.xoka74.chess.domain.models.Game
import com.xoka74.chess.domain.models.GameStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import io.crossbar.autobahn.websocket.WebSocketConnection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class GameViewModel @Inject constructor(
    private val wsConnection: WebSocketConnection,
    @Named("gameUrl") private val url: String,
) : ViewModel() {

    private val mutableUiState = MutableStateFlow<GameUiState?>(null)
    val uiState = mutableUiState.asStateFlow()

    fun sendMove(move: String) {
        val eventData = JSONObject(
            mapOf(
                "move" to move,
            )
        )
        wsConnection.sendMessage(
            JSONObject(
                mapOf(
                    "event_type" to "make_move",
                    "event_data" to eventData
                )
            ).toString()
        )
    }

    fun connectToGame(gameId: Int) {
        if (wsConnection.isConnected) return
        val gson = Gson()
        val gameUrl =
            "$url$gameId/?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjk1MTE0NDc2LCJpYXQiOjE2OTQ4OTg0NzYsImp0aSI6ImEyZTg0M2YwYzY5MjQ4ZmNiZGI4NTU3MjZlZGYyMzZhIiwidXNlcl9pZCI6MX0.04PE8eyDRYByouvis6WSXQMpQMlFnFVq9wGpLpi43jI"
        Log.i("GameUrl", gameUrl)
        val wsHandler = WebSocketHandler(onMessage = { event ->
            val data = event["result_data"].toString()
            when (event["type"]) {
                "join_game", "make_move", "end_session" -> {
                    val game = gson.fromJson(data, GameDto::class.java).toDomainModel()
                    mutableUiState.update {
                        GameUiState(game)
                    }
                }
            }
        }, onConnectCallback = {

        }, onCloseCallback = { code, reason ->
            wsConnection.reconnect()
        }, onOpenCallback = {

        })

        wsConnection.connect(gameUrl, wsHandler)
    }

    override fun onCleared() {
        wsConnection.sendClose()
        super.onCleared()
    }

    data class GameUiState(
        val game: Game,
    )
}