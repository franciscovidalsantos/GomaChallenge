package com.example.gomachallenge.data.model

import org.json.JSONObject

data class CryptoTickerDto(
    val symbol: String, val price: Double, val priceChange: Double, val priceChangePercent: Double
) {
    companion object {
        fun fromJson(json: String): CryptoTickerDto {
            val obj = JSONObject(json)
            val data = if (obj.has("data")) obj.getJSONObject("data") else obj
            return CryptoTickerDto(
                symbol = data.getString("s"),
                price = data.getDouble("c"),
                priceChange = data.getDouble("p"),
                priceChangePercent = data.getDouble("P")
            )
        }
    }
}
