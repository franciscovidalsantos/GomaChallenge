package com.example.gomachallenge.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
    crypto: Crypto, onClick: () -> Unit
) {
    val isPositive = crypto.priceChange >= 0
    val changeColor = if (isPositive) Color(0xFF4CAF50) else Color(0xFFF44336)
    val arrow = if (isPositive) "↑" else "↓"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp)
            .clickable { onClick() },
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Crypto icon and basic info
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)
            ) {
                val iconRes = getCryptoIcon(crypto.symbol)
                if (iconRes != null) {
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = "${crypto.symbol} logo",
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }

                Column {
                    Text(
                        text = crypto.symbol,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "$${"%.2f".format(crypto.price)}", // Format to 4 decimal places
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            // Price change and navigation arrow
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Column(
                    horizontalAlignment = Alignment.End, modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(
                        text = "$arrow $${"%.2f".format(crypto.priceChange)}", // Format to 4 decimal places
                        color = changeColor,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "${"%.2f".format(crypto.priceChangePercent)}%", // Format percentage to 2 decimal places
                        color = changeColor, style = MaterialTheme.typography.bodySmall
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = "View details",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}