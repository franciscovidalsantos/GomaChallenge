package com.example.gomachallenge

import app.cash.turbine.test
import com.example.gomachallenge.domain.model.Crypto
import com.example.gomachallenge.domain.repository.CryptoRepository
import com.example.gomachallenge.presentation.viewmodel.CryptoDetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CryptoDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = mockk<CryptoRepository>()
    private val testCrypto = Crypto(
        symbol = "BTCUSDT",
        price = 50000.0,
        priceChange = 1000.0,
        priceChangePercent = 2.0
    )

    private val updatedCrypto = Crypto(
        symbol = "BTCUSDT",
        price = 51000.0,
        priceChange = 2000.0,
        priceChangePercent = 4.0
    )

    @Test
    fun `initial state is correct`() = runTest {
        val viewModel = CryptoDetailViewModel(repository, testCrypto)

        viewModel.state.test {
            val initialState = awaitItem()
            assertEquals(testCrypto, initialState.crypto)
            assertFalse(initialState.isLoading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `startTracking updates crypto data`() = runTest {
        coEvery { repository.observeCryptoTicker(any()) } returns flowOf(listOf(updatedCrypto))

        val viewModel = CryptoDetailViewModel(repository, testCrypto)

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(updatedCrypto, state.crypto)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `startTracking handles errors`() = runTest {
        val errorMessage = "Update failed"
        coEvery { repository.observeCryptoTicker(any()) } returns flow {
            throw Exception(errorMessage)
        }

        val viewModel = CryptoDetailViewModel(repository, testCrypto)

        viewModel.snackBarMessage.test {
            assertEquals("Error updating BTC: $errorMessage", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}