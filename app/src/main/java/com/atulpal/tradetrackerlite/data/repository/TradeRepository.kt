package com.atulpal.tradetrackerlite.data.repository

import com.atulpal.tradetrackerlite.data.local.dao.TradeDao
import com.atulpal.tradetrackerlite.data.local.dao.UserProfileDao
import com.atulpal.tradetrackerlite.data.local.entity.TradeEntity
import com.atulpal.tradetrackerlite.data.local.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

class TradeRepository(
    private val tradeDao: TradeDao,
    private val userProfileDao: UserProfileDao
) {
    // Trades
    val allTrades: Flow<List<TradeEntity>> = tradeDao.getAllTrades()

    suspend fun getTradeById(id: Long): TradeEntity? = tradeDao.getTradeById(id)
    suspend fun insertTrade(trade: TradeEntity) = tradeDao.insertTrade(trade)
    suspend fun updateTrade(trade: TradeEntity) = tradeDao.updateTrade(trade)
    suspend fun deleteTrade(trade: TradeEntity) = tradeDao.deleteTrade(trade)

    // User Profile
    val userProfile: Flow<UserProfileEntity?> = userProfileDao.getUserProfile()

    suspend fun saveUserProfile(profile: UserProfileEntity) {
        userProfileDao.insertOrUpdateProfile(profile)
    }
}
