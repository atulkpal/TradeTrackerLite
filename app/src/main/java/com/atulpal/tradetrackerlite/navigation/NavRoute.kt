package com.atulpal.tradetrackerlite.navigation

sealed class NavRoute(val route: String) {
    object Onboarding : NavRoute("onboarding")
    object Dashboard : NavRoute("dashboard")
    object Analytics : NavRoute("analytics")
    object AddTrade : NavRoute("add_trade?tradeId={tradeId}") {
        fun createRoute(tradeId: Long? = null) = "add_trade?tradeId=${tradeId ?: -1L}"
    }
    object TradeDetails : NavRoute("trade_details/{tradeId}") {
        fun createRoute(tradeId: Long) = "trade_details/$tradeId"
    }
    object Settings : NavRoute("settings")
}
