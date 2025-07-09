package com.example.gomachallenge.presentation.screen

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gomachallenge.domain.model.Crypto
import com.example.gomachallenge.presentation.ui.CryptoItem
import com.example.gomachallenge.presentation.viewmodel.CryptoListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoListScreen(viewModel: CryptoListViewModel) {
    val mockList: List<Crypto> = viewModel.getMockData()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crypto Tracker", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue.copy(alpha = 0.4f))
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.padding(padding)
        ) {
            items(mockList) { crypto ->
                CryptoItem(crypto = crypto, onClick = { /*TODO:*/})
            }
        }
    }
}
