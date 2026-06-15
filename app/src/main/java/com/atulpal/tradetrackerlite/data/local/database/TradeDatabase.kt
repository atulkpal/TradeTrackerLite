package com.atulpal.tradetrackerlite.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.atulpal.tradetrackerlite.data.local.dao.TradeDao
import com.atulpal.tradetrackerlite.data.local.dao.UserProfileDao
import com.atulpal.tradetrackerlite.data.local.entity.TradeEntity
import com.atulpal.tradetrackerlite.data.local.entity.UserProfileEntity

@Database(
    entities = [TradeEntity::class, UserProfileEntity::class],
    version = 2,
    exportSchema = false
)
abstract class TradeDatabase : RoomDatabase() {
    abstract fun tradeDao(): TradeDao
    abstract fun userProfileDao(): UserProfileDao

    companion object {
        @Volatile
        private var INSTANCE: TradeDatabase? = null

        fun getDatabase(context: Context): TradeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TradeDatabase::class.java,
                    "trade_database"
                )
                .fallbackToDestructiveMigration() // Simplified for MVP/Development
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
