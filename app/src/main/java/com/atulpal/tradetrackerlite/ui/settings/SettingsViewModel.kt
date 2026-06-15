package com.atulpal.tradetrackerlite.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.atulpal.tradetrackerlite.data.local.entity.TradeEntity
import com.atulpal.tradetrackerlite.data.local.entity.UserProfileEntity
import com.atulpal.tradetrackerlite.data.repository.TradeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: TradeRepository) : ViewModel() {
    private val _isDarkMode = MutableStateFlow(true)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    val userProfile: StateFlow<UserProfileEntity?> = repository.userProfile.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun toggleDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
    }

    fun updateProfile(name: String, email: String?, phone: String?, currency: String, conversionRate: Double? = null) {
        viewModelScope.launch {
            val currentProfile = userProfile.value ?: return@launch
            val newProfile = currentProfile.copy(
                name = name,
                email = email,
                phoneNumber = phone,
                preferredCurrency = currency
            )
            repository.saveUserProfile(newProfile)

            // If conversion rate is provided, update all existing trades
            conversionRate?.let { rate ->
                val trades = repository.allTrades.first()
                trades.forEach { trade ->
                    val updatedTrade = trade.copy(
                        entryPrice = trade.entryPrice * rate,
                        exitPrice = trade.exitPrice * rate
                    )
                    repository.updateTrade(updatedTrade)
                }
            }
        }
    }
}

class SettingsViewModelFactory(private val repository: TradeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
