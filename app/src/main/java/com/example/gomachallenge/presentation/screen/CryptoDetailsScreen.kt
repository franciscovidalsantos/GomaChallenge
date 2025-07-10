package com.example.gomachallenge.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gomachallenge.presentation.ui.util.getCryptoIcon
import com.example.gomachallenge.presentation.viewmodel.CryptoDetailViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoDetailScreen(
    viewModel: CryptoDetailViewModel, onBack: () -> Unit
) {
    val uiState by viewModel.state.collectAsState()
    val snackBarMessage by viewModel.snackBarMessage.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackBarMessage) {
        snackBarMessage?.let { message ->
            snackBarHostState.showSnackbar(message)
            viewModel.dismissSnackBar()
        }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = uiState.crypto.symbol,
                    color = Color.White,
                )
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Blue.copy(alpha = 0.4f)
            ), navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack,
                        tint = Color.White,
                        contentDescription = "Back",
                    )
                }
            })
    }, snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    }) { padding ->
        val crypto = uiState.crypto
        val isPositive = crypto.priceChange >= 0
        val changeColor = if (isPositive) Color(0xFF4CAF50) else Color(0xFFF44336)
        val arrow = if (isPositive) "↑" else "↓"
        val iconRes = getCryptoIcon(crypto.symbol)

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (iconRes != null) {
                        Image(
                            painter = painterResource(id = iconRes),
                            contentDescription = "${crypto.symbol} logo",
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Current Price",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                        Text(
                            text = "$${"%.2f".format(crypto.price)}",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Market Data", style = MaterialTheme.typography.titleMedium
                    )

                    InfoRow(label = "Symbol", value = crypto.symbol)
                    InfoRow(label = "Price", value = "$${"%.2f".format(crypto.price)}")
                    InfoRow(
                        label = "24h Change",
                        value = "$arrow $${"%.2f".format(crypto.priceChange)} (${
                            "%.2f".format(
                                crypto.priceChangePercent
                            )
                        }%)",
                        valueColor = changeColor
                    )
                }
            }
        }
    }
}

@Composable
private fun InfoRow(
    label: String, value: String, valueColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = valueColor,
            fontWeight = FontWeight.Medium
        )
    }
}