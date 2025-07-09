package com.example.gomachallenge.domain.repository

import com.example.gomachallenge.domain.model.Crypto
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {
    /**
     * Observes real-time ticker data for the given list of symbols.
     */
    fun observeCryptoTicker(symbols: List<String>): Flow<List<Crypto>>
}
