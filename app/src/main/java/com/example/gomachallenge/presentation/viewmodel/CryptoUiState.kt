package com.example.gomachallenge.presentation.viewmodel

import com.example.gomachallenge.domain.model.Crypto

data class CryptoUiState(
    val cryptos: List<Crypto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

