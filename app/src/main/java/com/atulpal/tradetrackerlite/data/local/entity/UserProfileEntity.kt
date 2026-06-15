package com.atulpal.tradetrackerlite.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey
    val id: Int = 1, // Singleton profile
    val name: String,
    val email: String? = null,
    val phoneNumber: String? = null,
    val preferredCurrency: String = "INR",
    val initialBalance: Double = 0.0,
    val currentBalance: Double = 0.0,
    val createdAt: Long = System.currentTimeMillis()
)
