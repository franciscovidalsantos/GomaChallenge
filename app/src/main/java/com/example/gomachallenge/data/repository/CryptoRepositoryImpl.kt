package com.example.gomachallenge.data.repository

import com.example.gomachallenge.data.model.CryptoTickerDto
import com.example.gomachallenge.data.remote.CryptoWebSocketClient
import com.example.gomachallenge.domain.model.Crypto
import com.example.gomachallenge.domain.repository.CryptoRepository
import com.example.gomachallenge.util.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CryptoRepositoryImpl(
    private val client: CryptoWebSocketClient
) : CryptoRepository {
    override fun observeCryptoTicker(symbols: List<String>): Flow<List<Crypto>> {
        return client.observeTicker(symbols).map { json ->
            val dto = CryptoTickerDto.fromJson(json)
            listOf(dto.toDomain())
        }
    }
}
