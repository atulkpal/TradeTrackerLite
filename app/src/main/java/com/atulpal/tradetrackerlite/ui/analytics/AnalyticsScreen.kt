package com.atulpal.tradetrackerlite.ui.analytics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.atulpal.tradetrackerlite.ui.components.BentoSummaryCard
import com.atulpal.tradetrackerlite.ui.theme.DangerRed
import com.atulpal.tradetrackerlite.ui.theme.DarkSecondary
import com.atulpal.tradetrackerlite.ui.theme.DarkTertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(
    viewModel: AnalyticsViewModel
) {
    val state by viewModel.analyticsState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Performance Analytics",
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    BentoSummaryCard(
                        label = "Win Rate",
                        value = "${state.winRate.toInt()}%",
                        modifier = Modifier.weight(1f),
                        subValue = "Trending Up",
                        subValueColor = DarkSecondary
                    )
                    BentoSummaryCard(
                        label = "Profit Factor",
                        value = String.format("%.2f", state.profitFactor),
                        modifier = Modifier.weight(1f),
                        subValue = "Ideal: > 2.0",
                        subValueColor = DarkTertiary
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    BentoSummaryCard(
                        label = "Avg Profit",
                        value = "$${String.format("%.0f", state.avgProfit)}",
                        modifier = Modifier.weight(1f),
                        subValue = "Per Winning Trade",
                        subValueColor = DarkSecondary
                    )
                    BentoSummaryCard(
                        label = "Avg Loss",
                        value = "$${String.format("%.0f", state.avgLoss)}",
                        modifier = Modifier.weight(1f),
                        subValue = "Per Losing Trade",
                        subValueColor = DangerRed
                    )
                }
            }

            item {
                Text("Equity Curve (V2 Feature)", style = MaterialTheme.typography.titleMedium)
                Card(
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                ) {
                    Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        Text("Chart Placeholder", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            item {
                Text("Top Mistakes", style = MaterialTheme.typography.titleMedium)
                // Placeholder for mistakes list
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Mistake tracking coming in V2.", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}
