package com.atulpal.tradetrackerlite.ui.analytics

import androidx.lifecycle.*
import com.atulpal.tradetrackerlite.data.local.entity.TradeEntity
import com.atulpal.tradetrackerlite.data.repository.TradeRepository
import kotlinx.coroutines.flow.*

data class AnalyticsState(
    val winRate: Double = 0.0,
    val totalTrades: Int = 0,
    val avgProfit: Double = 0.0,
    val avgLoss: Double = 0.0,
    val profitFactor: Double = 0.0,
    val totalPL: Double = 0.0
)

class AnalyticsViewModel(private val repository: TradeRepository) : ViewModel() {

    val analyticsState: StateFlow<AnalyticsState> = repository.allTrades
        .map { trades ->
            calculateAnalytics(trades)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AnalyticsState()
        )

    private fun calculateAnalytics(trades: List<TradeEntity>): AnalyticsState {
        if (trades.isEmpty()) return AnalyticsState()

        val winningTrades = trades.filter { (it.exitPrice - it.entryPrice) * it.quantity * (if(it.side == "Buy") 1 else -1) > 0 }
        val losingTrades = trades.filter { (it.exitPrice - it.entryPrice) * it.quantity * (if(it.side == "Buy") 1 else -1) < 0 }

        val totalProfit = winningTrades.sumOf { (it.exitPrice - it.entryPrice) * it.quantity * (if(it.side == "Buy") 1 else -1) }
        val totalLoss = losingTrades.sumOf { (it.exitPrice - it.entryPrice) * it.quantity * (if(it.side == "Buy") 1 else -1) }.let { if (it < 0) -it else it }

        val totalTrades = trades.size
        val winRate = (winningTrades.size.toDouble() / totalTrades) * 100
        val avgProfit = if (winningTrades.isNotEmpty()) totalProfit / winningTrades.size else 0.0
        val avgLoss = if (losingTrades.isNotEmpty()) totalLoss / losingTrades.size else 0.0
        val profitFactor = if (totalLoss > 0) totalProfit / totalLoss else totalProfit
        val totalPL = totalProfit - totalLoss

        return AnalyticsState(
            winRate = winRate,
            totalTrades = totalTrades,
            avgProfit = avgProfit,
            avgLoss = avgLoss,
            profitFactor = profitFactor,
            totalPL = totalPL
        )
    }
}

class AnalyticsViewModelFactory(private val repository: TradeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnalyticsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnalyticsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
