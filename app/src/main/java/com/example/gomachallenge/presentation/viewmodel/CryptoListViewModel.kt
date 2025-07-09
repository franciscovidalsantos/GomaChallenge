package com.example.gomachallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gomachallenge.domain.model.Crypto
import com.example.gomachallenge.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CryptoUiState(
    val cryptos: List<Crypto> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class CryptoListViewModel(
    private val repository: CryptoRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CryptoUiState())
    val state: StateFlow<CryptoUiState> = _state

    private val cache = mutableMapOf<String, Crypto>()

    init {
        startTracking()
    }

    private fun startTracking() {
        val symbols = listOf("btcusdt", "ethusdt", "adausdt", "solusdt", "xrpusdt")

        viewModelScope.launch {
            repository.observeCryptoTicker(symbols)
                .collect { updates ->
                    updates.forEach { crypto ->
                        cache[crypto.symbol] = crypto
                    }
                    _state.update {
                        it.copy(
                            cryptos = cache.values.sortedBy { c -> c.symbol },
                            isLoading = false,
                            error = null
                        )
                    }
                }
        }
    }

//    fun getMockData(): List<Crypto> {
//        return listOf(
//            Crypto("BTCUSDT", 30000.0, 150.0, 0.5),
//            Crypto("ETHUSDT", 2000.0, -30.0, -1.2),
//            Crypto("ADAUSDT", 0.45, 0.01, 2.3),
//            Crypto("SOLUSDT", 35.0, 1.5, 4.4),
//            Crypto("XRPUSDT", 0.60, -0.02, -3.3),
//        )
//    }
}
