package com.atulpal.tradetrackerlite.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.atulpal.tradetrackerlite.navigation.NavRoute

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        NavRoute.Dashboard,
        NavRoute.Analytics,
        NavRoute.Settings
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = 8.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = { 
                    Icon(
                        imageVector = when(screen) {
                            NavRoute.Dashboard -> Icons.Default.Dashboard
                            NavRoute.Analytics -> Icons.Default.Analytics
                            NavRoute.Settings -> Icons.Default.Settings
                            else -> Icons.Default.Psychology
                        },
                        contentDescription = screen.route
                    )
                },
                label = { Text(screen.route.replaceFirstChar { it.uppercase() }) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
