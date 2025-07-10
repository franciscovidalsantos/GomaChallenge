package com.example.gomachallenge

import app.cash.turbine.test
import com.example.gomachallenge.data.remote.CryptoWebSocketClient
import com.example.gomachallenge.domain.model.Crypto
import com.example.gomachallenge.domain.repository.CryptoRepository
import com.example.gomachallenge.presentation.viewmodel.CryptoListViewModel
import com.example.gomachallenge.presentation.viewmodel.CryptoUiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CryptoListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = mockk<CryptoRepository>()
    private val webSocketClient = mockk<CryptoWebSocketClient>()
    private val testCryptos = listOf(
        Crypto("BTCUSDT", 50000.0, 1000.0, 2.0),
        Crypto("ETHUSDT", 3000.0, 50.0, 1.67)
    )

    @Test
    fun `initial state is correct`() = runTest {
        val viewModel = CryptoListViewModel(repository, webSocketClient)

        viewModel.state.test {
            val initialState = awaitItem()
            assertTrue(initialState.cryptos.isEmpty())
            assertFalse(initialState.isLoading)
            assertNull(initialState.error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `startTracking updates state`() = runTest {
        coEvery { repository.observeCryptoTicker(any()) } returns flowOf(testCryptos)

        val viewModel = CryptoListViewModel(repository, webSocketClient)
        viewModel.startTracking()

        viewModel.state.test {
            // Initial loading state
            val state = awaitItem()
            assertTrue(state.isLoading)

            // Success state
            val successState = awaitItem()
            assertFalse(successState.isLoading)
            assertEquals(2, successState.cryptos.size)
            assertEquals("BTCUSDT", successState.cryptos[0].symbol)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { repository.observeCryptoTicker(any()) }
    }

    @Test
    fun `startTracking handles errors`() = runTest {
        val errorMessage = "Network error"
        coEvery { repository.observeCryptoTicker(any()) } returns flow {
            throw Exception(errorMessage)
        }

        val viewModel = CryptoListViewModel(repository, webSocketClient)
        viewModel.startTracking()

        viewModel.state.test {
            // Loading state
            assertTrue(awaitItem().isLoading)

            // Error state
            val errorState = awaitItem()
            assertFalse(errorState.isLoading)
            assertEquals(errorMessage, errorState.error)
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.snackBarMessage.test {
            assertEquals("Connection error: $errorMessage.", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadData resets state and starts tracking`() = runTest {
        coEvery { repository.observeCryptoTicker(any()) } returns flowOf(testCryptos)

        val viewModel = CryptoListViewModel(repository, webSocketClient)
        viewModel.loadData()

        viewModel.state.test {
            // First state - Reset with loading (isLoading=true)
            val loadingState = awaitItem()
            assertEquals(
                CryptoUiState(cryptos = emptyList(), isLoading = true, error = null),
                loadingState
            )

            // Second state - Load Data (isLoading=false)
            val successState = awaitItem()
            assertFalse(successState.isLoading)
            assertEquals(2, successState.cryptos.size)
            cancelAndIgnoreRemainingEvents()
        }
    }
}