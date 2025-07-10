package com.example.gomachallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gomachallenge.domain.model.Crypto
import com.example.gomachallenge.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CryptoDetailViewModel(
    private val repository: CryptoRepository, initialCrypto: Crypto
) : ViewModel() {
    private val _state = MutableStateFlow(CryptoDetailState(crypto = initialCrypto))
    val state: StateFlow<CryptoDetailState> = _state

    private val _snackBarMessage = MutableStateFlow<String?>(null)
    val snackBarMessage: StateFlow<String?> = _snackBarMessage

    init {
        startTracking(initialCrypto.symbol)
    }

    private fun startTracking(symbol: String) {
        viewModelScope.launch {
            try {
                repository.observeCryptoTicker(listOf(symbol)).collect { cryptoList ->
                    cryptoList.firstOrNull()?.let { updatedCrypto ->
                        _state.update { it.copy(crypto = updatedCrypto) }
                    }
                }
            } catch (e: Exception) {
                _snackBarMessage.value =
                    "Error updating ${symbol.replace("USDT", "")}: ${e.message ?: "Unknown error"}"
            }
        }
    }

    fun dismissSnackBar() {
        _snackBarMessage.value = null
    }
}

data class CryptoDetailState(
    val crypto: Crypto, val isLoading: Boolean = false
)