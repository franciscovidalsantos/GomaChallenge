package com.example.gomachallenge.domain.model

data class Crypto(
    val symbol: String,
    val price: Double,
    val priceChange: Double,
    val priceChangePercent: Double
)
