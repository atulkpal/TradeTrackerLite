package com.atulpal.tradetrackerlite.ui.trade

import androidx.lifecycle.*
import com.atulpal.tradetrackerlite.data.local.entity.TradeEntity
import com.atulpal.tradetrackerlite.data.repository.TradeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TradeViewModel(private val repository: TradeRepository) : ViewModel() {

    val allTrades: StateFlow<List<TradeEntity>> = repository.allTrades.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun insertTrade(trade: TradeEntity) {
        viewModelScope.launch {
            repository.insertTrade(trade)
        }
    }

    fun updateTrade(trade: TradeEntity) {
        viewModelScope.launch {
            repository.updateTrade(trade)
        }
    }

    fun deleteTrade(trade: TradeEntity) {
        viewModelScope.launch {
            repository.deleteTrade(trade)
        }
    }

    suspend fun getTradeById(id: Long): TradeEntity? {
        return repository.getTradeById(id)
    }
}

class TradeViewModelFactory(private val repository: TradeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TradeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TradeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
