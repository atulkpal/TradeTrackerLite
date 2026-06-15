package com.atulpal.tradetrackerlite.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atulpal.tradetrackerlite.data.local.entity.TradeEntity
import com.atulpal.tradetrackerlite.ui.theme.DangerRed
import com.atulpal.tradetrackerlite.ui.theme.SuccessGreen
import java.util.*

@Composable
fun TradeCard(
    trade: TradeEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    currencySymbol: String = "₹"
) {
    val pl = (trade.exitPrice - trade.entryPrice) * trade.quantity * (if (trade.side == "Buy") 1 else -1)
    val isProfit = pl >= 0

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainerHighest,
                            shape = MaterialTheme.shapes.medium
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = trade.symbol.first().toString(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = trade.symbol,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${trade.marketType} • ${trade.side}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${if (isProfit) "+" else ""}$currencySymbol${String.format(Locale.US, "%.2f", pl)}",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isProfit) SuccessGreen else DangerRed
                )
                Text(
                    text = if (isProfit) "PROFIT" else "LOSS",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isProfit) SuccessGreen else DangerRed
                )
            }
        }
    }
}
