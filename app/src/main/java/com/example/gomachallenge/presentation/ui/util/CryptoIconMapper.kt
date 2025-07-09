package com.example.gomachallenge.presentation.ui.util

import androidx.annotation.DrawableRes
import com.example.gomachallenge.R

@DrawableRes
fun getCryptoIcon(symbol: String): Int? {
    return when (symbol.lowercase()) {
        "btcusdt" -> R.drawable.btc
        "ethusdt" -> R.drawable.eth
        "adausdt" -> R.drawable.ada
        "solusdt" -> R.drawable.sol
        "xrpusdt" -> R.drawable.xrp
        else -> null
    }
}
