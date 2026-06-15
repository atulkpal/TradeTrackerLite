package com.atulpal.tradetrackerlite.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atulpal.tradetrackerlite.ui.components.BentoSummaryCard
import com.atulpal.tradetrackerlite.ui.components.TradeCard
import com.atulpal.tradetrackerlite.ui.onboarding.OnboardingViewModel
import com.atulpal.tradetrackerlite.ui.theme.DarkTertiary
import com.atulpal.tradetrackerlite.ui.trade.TradeViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: TradeViewModel,
    onboardingViewModel: OnboardingViewModel,
    onAddTradeClick: () -> Unit,
    onTradeClick: (Long) -> Unit
) {
    val trades by viewModel.allTrades.collectAsState()
    val userProfile by onboardingViewModel.userProfile.collectAsState()
    
    val currencySymbol = when(userProfile?.preferredCurrency) {
        "INR" -> "₹"
        "USD" -> "$"
        "EUR" -> "€"
        "GBP" -> "£"
        "JPY" -> "¥"
        "AED" -> "DH"
        "SGD" -> "S$"
        else -> "₹"
    }

    // Simple summary calculations
    val totalPL = trades.sumOf { (it.exitPrice - it.entryPrice) * it.quantity * (if (it.side == "Buy") 1 else -1) }
    val winRate = if (trades.isNotEmpty()) {
        val wins = trades.count { (it.exitPrice - it.entryPrice) * it.quantity * (if (it.side == "Buy") 1 else -1) > 0 }
        (wins.toDouble() / trades.size * 100).toInt()
    } else 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Trade Journal Lite",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    ) 
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTradeClick,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Trade")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Good morning, ${userProfile?.name ?: "Trader"}",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    "Market is open. Ready for the next play?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    BentoSummaryCard(
                        label = "Total P/L",
                        value = "${if (totalPL >= 0) "+" else ""}$currencySymbol${String.format(Locale.US, "%.2f", totalPL)}",
                        modifier = Modifier.fillMaxWidth(),
                        subValue = "+12.4% this month",
                        subValueColor = DarkTertiary,
                        icon = {
                            Icon(Icons.Default.TrendingUp, contentDescription = null, tint = DarkTertiary)
                        }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        BentoSummaryCard(
                            label = "Total Trades",
                            value = trades.size.toString(),
                            modifier = Modifier.weight(1f)
                        )
                        BentoSummaryCard(
                            label = "Win Rate",
                            value = "$winRate%",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Recent Trades", style = MaterialTheme.typography.titleMedium)
                    TextButton(onClick = { /* TODO */ }) {
                        Text("View All")
                    }
                }
            }

            items(trades.take(10)) { trade ->
                TradeCard(
                    trade = trade,
                    currencySymbol = currencySymbol,
                    onClick = { onTradeClick(trade.id) }
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}
