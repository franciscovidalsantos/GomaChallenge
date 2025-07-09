package com.example.gomachallenge.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gomachallenge.domain.model.Crypto


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoDetailScreen(crypto: Crypto, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details for ${crypto.symbol}", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue.copy(alpha = 0.4f)),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            tint = Color.White,
                            contentDescription = "Back",
                        )
                    }
                })

        }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text("Symbol: ${crypto.symbol}")
            Text("Price: ${crypto.price}")
            Text("Change: ${crypto.priceChange}")
            Text("Change: ${crypto.priceChangePercent}%")
        }
    }
}
