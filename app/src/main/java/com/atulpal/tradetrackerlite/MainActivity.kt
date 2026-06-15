package com.atulpal.tradetrackerlite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.atulpal.tradetrackerlite.navigation.NavGraph
import com.atulpal.tradetrackerlite.navigation.NavRoute
import com.atulpal.tradetrackerlite.ui.components.BottomNavigationBar
import com.atulpal.tradetrackerlite.ui.settings.SettingsViewModel
import com.atulpal.tradetrackerlite.ui.settings.SettingsViewModelFactory
import com.atulpal.tradetrackerlite.ui.theme.TradeTrackerLiteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val application = application as TradeTrackerApplication
        
        setContent {
            val settingsViewModel: SettingsViewModel = viewModel(
                factory = SettingsViewModelFactory(application.repository)
            )
            val isDarkMode by settingsViewModel.isDarkMode.collectAsState()

            TradeTrackerLiteTheme(darkTheme = isDarkMode) {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                
                val showBottomBar = currentRoute in listOf(
                    NavRoute.Dashboard.route,
                    NavRoute.Analytics.route,
                    NavRoute.Settings.route
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavigationBar(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        application = application,
                        settingsViewModel = settingsViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
