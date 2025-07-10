package com.example.gomachallenge

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gomachallenge.data.repository.CryptoRepositoryImpl
import com.example.gomachallenge.domain.model.Crypto
import com.example.gomachallenge.presentation.screen.CryptoDetailScreen
import com.example.gomachallenge.presentation.screen.CryptoListScreen
import com.example.gomachallenge.presentation.viewmodel.CryptoDetailViewModel
import com.example.gomachallenge.presentation.viewmodel.CryptoListViewModel

@Composable
fun AppNavigation(
    listViewModel: CryptoListViewModel, repository: CryptoRepositoryImpl
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            CryptoListScreen(
                viewModel = listViewModel, onCryptoClick = { crypto ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("crypto", crypto)
                    navController.navigate("detail")
                })
        }
        composable("detail") {
            val crypto =
                navController.previousBackStackEntry?.savedStateHandle?.get<Crypto>("crypto")
            val detailViewModel = remember {
                CryptoDetailViewModel(
                    repository = repository,
                    initialCrypto = crypto ?: throw IllegalStateException("Crypto data not found")
                )
            }

            crypto?.let {
                CryptoDetailScreen(
                    viewModel = detailViewModel, onBack = { navController.popBackStack() })
            }
        }
    }
}