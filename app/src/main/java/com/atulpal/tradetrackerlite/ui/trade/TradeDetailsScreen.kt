package com.atulpal.tradetrackerlite.ui.trade

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atulpal.tradetrackerlite.data.local.entity.TradeEntity
import com.atulpal.tradetrackerlite.ui.theme.DangerRed
import com.atulpal.tradetrackerlite.ui.theme.DataLarge
import com.atulpal.tradetrackerlite.ui.theme.DarkTertiary
import com.atulpal.tradetrackerlite.ui.theme.SuccessGreen
import com.atulpal.tradetrackerlite.utils.CurrencyUtils
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TradeDetailsScreen(
    viewModel: TradeViewModel,
    tradeId: Long,
    onEditClick: (Long) -> Unit,
    onNavigateBack: () -> Unit
) {
    var trade by remember { mutableStateOf<TradeEntity?>(null) }
    val userProfile by viewModel.userProfile.collectAsState()
    val currencySymbol = CurrencyUtils.getCurrencySymbol(userProfile?.preferredCurrency)
    
    LaunchedEffect(tradeId) {
        trade = viewModel.getTradeById(tradeId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(trade?.symbol ?: "Trade Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { onEditClick(tradeId) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = { 
                        trade?.let { 
                            viewModel.deleteTrade(it)
                            onNavigateBack()
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = DangerRed)
                    }
                }
            )
        }
    ) { padding ->
        trade?.let { t ->
            val pl = (t.exitPrice - t.entryPrice) * t.quantity * (if (t.side == "Buy") 1 else -1)
            val isProfit = pl >= 0
            val roi = if (t.entryPrice > 0) (pl / (t.entryPrice * t.quantity) * 100) else 0.0

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Hero P/L Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    ),
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Total Profit/Loss",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            "${if (isProfit) "+" else ""}$currencySymbol${String.format(Locale.US, "%.2f", pl)}",
                            style = DataLarge.copy(fontSize = 40.sp),
                            color = if (isProfit) DarkTertiary else DangerRed
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.TrendingUp,
                                contentDescription = null,
                                tint = if (isProfit) SuccessGreen else DangerRed,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                "${String.format(Locale.US, "%.1f", roi)}%",
                                style = MaterialTheme.typography.titleMedium,
                                color = if (isProfit) SuccessGreen else DangerRed
                            )
                        }
                    }
                }

                // Metrics Grid
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        MetricCard("Entry Price", "$currencySymbol${t.entryPrice}", Modifier.weight(1f))
                        MetricCard("Exit Price", "$currencySymbol${t.exitPrice}", Modifier.weight(1f))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        MetricCard("Quantity", t.quantity.toString(), Modifier.weight(1f))
                        MetricCard("Side", t.side, Modifier.weight(1f))
                    }
                }

                // Notes
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Trade Notes", style = MaterialTheme.typography.titleMedium)
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            t.notes.ifEmpty { "No notes provided." },
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        } ?: Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun MetricCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, style = DataLarge.copy(fontSize = 18.sp))
        }
    }
}
