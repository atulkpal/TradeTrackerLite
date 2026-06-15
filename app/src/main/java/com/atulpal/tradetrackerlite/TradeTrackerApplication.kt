package com.atulpal.tradetrackerlite

import android.app.Application
import com.atulpal.tradetrackerlite.data.local.database.TradeDatabase
import com.atulpal.tradetrackerlite.data.repository.TradeRepository

class TradeTrackerApplication : Application() {
    val database by lazy { TradeDatabase.getDatabase(this) }
    val repository by lazy { 
        TradeRepository(
            tradeDao = database.tradeDao(),
            userProfileDao = database.userProfileDao()
        ) 
    }
}
