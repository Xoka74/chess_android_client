package com.xoka74.chess.data.websockets

import android.util.Log
import io.crossbar.autobahn.websocket.WebSocketConnectionHandler
import io.crossbar.autobahn.websocket.types.ConnectionResponse
import org.json.JSONObject

class WebSocketHandler(
    private val onMessage: (JSONObject) -> (Unit),
    private val onConnectCallback: (response: ConnectionResponse?) -> (Unit),
    private val onCloseCallback: (code: Int, reason: String?) -> (Unit),
    private val onOpenCallback: (Unit) -> (Unit),
) : WebSocketConnectionHandler() {

    override fun onConnect(response: ConnectionResponse?) {
        Log.i("onConnect", "Connection response: ${response.toString()}")
        this.onConnectCallback(response)
    }


    override fun onOpen() {
        Log.i("onOpen", "Connection is open!")
//        onOpenCallback()
    }


    override fun onClose(code: Int, reason: String?) {
        Log.i("onClose", "Connection is closed with code $code due $reason")
        onCloseCallback(code, reason)
    }


    override fun onMessage(message: String) {
        Log.i("onMessage", message)
        val eventData = JSONObject(message)
        onMessage(eventData)
    }
}