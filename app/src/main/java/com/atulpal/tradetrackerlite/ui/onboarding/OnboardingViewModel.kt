package com.atulpal.tradetrackerlite.ui.onboarding

import androidx.lifecycle.*
import com.atulpal.tradetrackerlite.data.local.entity.UserProfileEntity
import com.atulpal.tradetrackerlite.data.repository.TradeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OnboardingViewModel(private val repository: TradeRepository) : ViewModel() {

    val userProfile: StateFlow<UserProfileEntity?> = repository.userProfile.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun saveProfile(name: String, email: String, phone: String, currency: String) {
        viewModelScope.launch {
            val profile = UserProfileEntity(
                name = name,
                email = email.ifBlank { null },
                phoneNumber = phone.ifBlank { null },
                preferredCurrency = currency
            )
            repository.saveUserProfile(profile)
        }
    }
}

class OnboardingViewModelFactory(private val repository: TradeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OnboardingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OnboardingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
