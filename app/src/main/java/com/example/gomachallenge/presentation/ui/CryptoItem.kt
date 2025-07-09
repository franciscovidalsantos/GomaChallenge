package com.example.gomachallenge.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gomachallenge.domain.model.Crypto
import com.example.gomachallenge.presentation.ui.util.getCryptoIcon

@Composable
fun CryptoItem(
    crypto: Crypto,
    onClick: () -> Unit
) {
    val isPositive = crypto.priceChange >= 0
    val changeColor = if (isPositive) Color(0xFF4CAF50) else Color(0xFFF44336)
    val arrow = if (isPositive) "↑" else "↓"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(/* TODO: Navigation to details screen  */) },
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,

            ) {
            val iconRes = getCryptoIcon(crypto.symbol)
            if (iconRes != null) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = "${crypto.symbol} logo",
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(crypto.symbol, fontWeight = FontWeight.Bold)
                Text("Price: $${crypto.price}")
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$arrow ${crypto.priceChange} (${crypto.priceChangePercent}%)",
                    color = changeColor,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
