package com.atulpal.tradetrackerlite.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.atulpal.tradetrackerlite.TradeTrackerApplication
import com.atulpal.tradetrackerlite.ui.analytics.AnalyticsScreen
import com.atulpal.tradetrackerlite.ui.analytics.AnalyticsViewModel
import com.atulpal.tradetrackerlite.ui.analytics.AnalyticsViewModelFactory
import com.atulpal.tradetrackerlite.ui.dashboard.DashboardScreen
import com.atulpal.tradetrackerlite.ui.onboarding.OnboardingScreen
import com.atulpal.tradetrackerlite.ui.onboarding.OnboardingViewModel
import com.atulpal.tradetrackerlite.ui.onboarding.OnboardingViewModelFactory
import com.atulpal.tradetrackerlite.ui.settings.SettingsScreen
import com.atulpal.tradetrackerlite.ui.settings.SettingsViewModel
import com.atulpal.tradetrackerlite.ui.trade.*

@Composable
fun NavGraph(
    navController: NavHostController,
    application: TradeTrackerApplication,
    settingsViewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) {
    val tradeViewModel: TradeViewModel = viewModel(
        factory = TradeViewModelFactory(application.repository)
    )
    val analyticsViewModel: AnalyticsViewModel = viewModel(
        factory = AnalyticsViewModelFactory(application.repository)
    )
    val onboardingViewModel: OnboardingViewModel = viewModel(
        factory = OnboardingViewModelFactory(application.repository)
    )

    val userProfile by onboardingViewModel.userProfile.collectAsState()

    // Determine start destination based on profile existence
    val startDestination = if (userProfile == null) NavRoute.Onboarding.route else NavRoute.Dashboard.route

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(NavRoute.Onboarding.route) {
            OnboardingScreen(
                viewModel = onboardingViewModel,
                onComplete = {
                    navController.navigate(NavRoute.Dashboard.route) {
                        popUpTo(NavRoute.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(NavRoute.Dashboard.route) {
            DashboardScreen(
                viewModel = tradeViewModel,
                onboardingViewModel = onboardingViewModel,
                onAddTradeClick = { navController.navigate(NavRoute.AddTrade.createRoute()) },
                onTradeClick = { id -> navController.navigate(NavRoute.TradeDetails.createRoute(id)) }
            )
        }
        composable(NavRoute.Analytics.route) {
            AnalyticsScreen(viewModel = analyticsViewModel)
        }
        composable(
            route = NavRoute.AddTrade.route,
            arguments = listOf(navArgument("tradeId") { 
                type = NavType.LongType
                defaultValue = -1L
            })
        ) { backStackEntry ->
            val tradeId = backStackEntry.arguments?.getLong("tradeId") ?: -1L
            AddTradeScreen(
                viewModel = tradeViewModel,
                tradeId = tradeId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = NavRoute.TradeDetails.route,
            arguments = listOf(navArgument("tradeId") { type = NavType.LongType })
        ) { backStackEntry ->
            val tradeId = backStackEntry.arguments?.getLong("tradeId") ?: -1L
            TradeDetailsScreen(
                viewModel = tradeViewModel,
                tradeId = tradeId,
                onEditClick = { id -> navController.navigate(NavRoute.AddTrade.createRoute(id)) },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(NavRoute.Settings.route) {
            SettingsScreen(viewModel = settingsViewModel)
        }
    }
}
