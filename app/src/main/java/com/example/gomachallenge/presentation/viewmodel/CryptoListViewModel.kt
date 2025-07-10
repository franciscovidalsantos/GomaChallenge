package com.example.gomachallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gomachallenge.data.remote.CryptoWebSocketClient
import com.example.gomachallenge.domain.model.Crypto
import com.example.gomachallenge.domain.repository.CryptoRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CryptoListViewModel(
    private val repository: CryptoRepository,
    private val webSocketClient: CryptoWebSocketClient,

    ) : ViewModel() {
    private val _state = MutableStateFlow(CryptoUiState())
    val state: StateFlow<CryptoUiState> = _state

    private val _snackBarMessage = MutableStateFlow<String?>(null)
    val snackBarMessage: StateFlow<String?> = _snackBarMessage

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
                    async { delay(250) }.await()
                }


            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error",
                    )
                }
                _snackBarMessage.value =
                    "Connection error: ${e.message ?: "Please check your internet connection"}."
                hasStarted = false
            }
        }
    }

    fun dismissSnackBar() {
        _snackBarMessage.value = null
    }

    fun loadData() {
        hasStarted = false
        cache.clear()
        _state.value = CryptoUiState()
        startTracking()

    }
}
