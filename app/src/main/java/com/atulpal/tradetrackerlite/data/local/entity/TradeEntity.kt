package com.atulpal.tradetrackerlite.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trades")
data class TradeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val symbol: String,
    val marketType: String, // Stock, Forex, Crypto
    val side: String, // Buy, Sell
    val quantity: Double,
    val entryPrice: Double,
    val exitPrice: Double,
    val entryTime: Long,
    val exitTime: Long,
    val setup: String,
    val emotion: String,
    val notes: String,
    val isFavorite: Boolean = false
)
