package com.atulpal.tradetrackerlite.ui.trade

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.atulpal.tradetrackerlite.data.local.entity.TradeEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTradeScreen(
    viewModel: TradeViewModel,
    tradeId: Long = -1L,
    onNavigateBack: () -> Unit
) {
    var symbol by remember { mutableStateOf("") }
    var marketType by remember { mutableStateOf("Stock") }
    var side by remember { mutableStateOf("Buy") }
    var quantity by remember { mutableStateOf("") }
    var entryPrice by remember { mutableStateOf("") }
    var exitPrice by remember { mutableStateOf("") }
    var setup by remember { mutableStateOf("Breakout") }
    var emotion by remember { mutableStateOf("Neutral") }
    var notes by remember { mutableStateOf("") }

    LaunchedEffect(tradeId) {
        if (tradeId != -1L) {
            viewModel.getTradeById(tradeId)?.let { trade ->
                symbol = trade.symbol
                marketType = trade.marketType
                side = trade.side
                quantity = trade.quantity.toString()
                entryPrice = trade.entryPrice.toString()
                exitPrice = trade.exitPrice.toString()
                setup = trade.setup
                emotion = trade.emotion
                notes = trade.notes
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (tradeId == -1L) "Add New Trade" else "Edit Trade") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = symbol,
                onValueChange = { symbol = it.uppercase() },
                label = { Text("Symbol (e.g., AAPL)") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Stock", "Forex", "Crypto").forEach { type ->
                    FilterChip(
                        selected = marketType == type,
                        onClick = { marketType = type },
                        label = { Text(type) }
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Buy", "Sell").forEach { s ->
                    FilterChip(
                        selected = side == s,
                        onClick = { side = s },
                        label = { Text(s) }
                    )
                }
            }

            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantity") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = entryPrice,
                    onValueChange = { entryPrice = it },
                    label = { Text("Entry Price") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = exitPrice,
                    onValueChange = { exitPrice = it },
                    label = { Text("Exit Price") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes & Reflections") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Button(
                onClick = {
                    val trade = TradeEntity(
                        id = if (tradeId == -1L) 0 else tradeId,
                        symbol = symbol,
                        marketType = marketType,
                        side = side,
                        quantity = quantity.toDoubleOrNull() ?: 0.0,
                        entryPrice = entryPrice.toDoubleOrNull() ?: 0.0,
                        exitPrice = exitPrice.toDoubleOrNull() ?: 0.0,
                        entryTime = System.currentTimeMillis(),
                        exitTime = System.currentTimeMillis(),
                        setup = setup,
                        emotion = emotion,
                        notes = notes
                    )
                    if (tradeId == -1L) viewModel.insertTrade(trade) else viewModel.updateTrade(trade)
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Save Trade")
            }
        }
    }
}
