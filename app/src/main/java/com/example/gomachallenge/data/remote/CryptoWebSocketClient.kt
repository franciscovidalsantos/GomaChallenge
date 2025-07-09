package com.example.gomachallenge.data.remote

import kotlinx.coroutines.flow.Flow

interface CryptoWebSocketClient {
    fun observeTicker(symbols: List<String>): Flow<String>
    fun close()
}