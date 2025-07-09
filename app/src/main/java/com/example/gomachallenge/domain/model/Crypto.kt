package com.example.gomachallenge.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Crypto(
    val symbol: String,
    val price: Double,
    val priceChange: Double,
    val priceChangePercent: Double
) : Parcelable
