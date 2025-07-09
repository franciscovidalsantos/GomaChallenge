package com.example.gomachallenge

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.gomachallenge.presentation.screen.CryptoDetailScreen
import com.example.gomachallenge.presentation.screen.CryptoListScreen
import com.example.gomachallenge.presentation.viewmodel.CryptoListViewModel
import com.example.gomachallenge.domain.model.Crypto
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavigation(viewModel: CryptoListViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            CryptoListScreen(
                viewModel = viewModel, onCryptoClick = { crypto ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("crypto", crypto)
                    navController.navigate("detail")
                })
        }
        composable("detail") {
            val crypto =
                navController.previousBackStackEntry?.savedStateHandle?.get<Crypto>("crypto")

            crypto?.let {
                CryptoDetailScreen(crypto = it, onBack = { navController.popBackStack() })
            }
        }
    }
}
