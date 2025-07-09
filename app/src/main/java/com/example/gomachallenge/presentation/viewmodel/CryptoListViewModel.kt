package com.example.gomachallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gomachallenge.domain.model.Crypto


class CryptoListViewModel(
) : ViewModel() {

    fun getMockData(): List<Crypto> {
        return listOf(
            Crypto("BTCUSDT", 30000.0, 150.0, 0.5),
            Crypto("ETHUSDT", 2000.0, -30.0, -1.2),
            Crypto("ADAUSDT", 0.45, 0.01, 2.3),
            Crypto("SOLUSDT", 35.0, 1.5, 4.4),
            Crypto("XRPUSDT", 0.60, -0.02, -3.3),
        )
    }
}
