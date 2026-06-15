package com.atulpal.tradetrackerlite.utils

object CurrencyUtils {
    fun getCurrencySymbol(currencyCode: String?): String {
        return when (currencyCode) {
            "INR" -> "₹"
            "USD" -> "$"
            "EUR" -> "€"
            "GBP" -> "£"
            "JPY" -> "¥"
            "AED" -> "DH"
            "SGD" -> "S$"
            else -> "₹"
        }
    }
}
