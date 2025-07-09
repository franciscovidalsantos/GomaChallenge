package com.example.gomachallenge.util

import com.example.gomachallenge.data.model.CryptoTickerDto
import com.example.gomachallenge.domain.model.Crypto

fun CryptoTickerDto.toDomain(): Crypto {
    return Crypto(
        symbol = symbol,
        price = price,
        priceChange = priceChange,
        priceChangePercent = priceChangePercent
    )
}
