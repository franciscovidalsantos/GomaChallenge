package com.example.gomachallenge.data.remote

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.*

class BinanceWebSocketClient : CryptoWebSocketClient {

    private var webSocket: WebSocket? = null
    private val client = OkHttpClient()

    override fun observeTicker(symbols: List<String>): Flow<String> = callbackFlow {
        val url = buildSocketUrl(symbols)
        val request = Request.Builder().url(url).build()

        val listener = object : WebSocketListener() {
            override fun onOpen(ws: WebSocket, response: Response) {
                webSocket = ws
            }

            override fun onMessage(ws: WebSocket, text: String) {
                trySend(text)
            }

            override fun onFailure(ws: WebSocket, t: Throwable, response: Response?) {
                close(t)
            }

            override fun onClosing(ws: WebSocket, code: Int, reason: String) {
                ws.close(code, reason)
                close()
            }
        }

        client.newWebSocket(request, listener)

        awaitClose {
            webSocket?.close(1000, "User closed")
            webSocket = null
        }
    }

    override fun close() {
        webSocket?.close(1000, "Closed manually")
        webSocket = null
    }

    private fun buildSocketUrl(symbols: List<String>): String {
        val joined = symbols.joinToString("/") { "${it.lowercase()}@ticker" }
        return "wss://stream.binance.com:9443/stream?streams=$joined"
    }
}
