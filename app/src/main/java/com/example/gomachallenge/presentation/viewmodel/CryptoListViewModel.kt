package com.example.gomachallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gomachallenge.domain.model.Crypto
import com.example.gomachallenge.domain.repository.CryptoRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CryptoUiState(
    val cryptos: List<Crypto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class CryptoListViewModel(
    private val repository: CryptoRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CryptoUiState())
    val state: StateFlow<CryptoUiState> = _state

    private val cache = mutableMapOf<String, Crypto>()

    private var hasStarted = false


    fun startTracking() {
        if (hasStarted) return
        hasStarted = true
        val symbols = listOf("btcusdt", "ethusdt", "adausdt", "solusdt", "xrpusdt")

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }


            try {
                async { delay(1000) }.await()

                repository.observeCryptoTicker(symbols).collect { cryptoList ->
                    cryptoList.forEach { crypto ->
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


            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message ?: "Unknown error") }
                hasStarted = false
            }
        }
    }

    fun loadData() {
        hasStarted = false
        cache.clear()
        _state.value = CryptoUiState()
        startTracking()

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
