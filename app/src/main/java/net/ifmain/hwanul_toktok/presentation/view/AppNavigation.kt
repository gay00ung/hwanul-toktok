package net.ifmain.hwanul_toktok.presentation.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "exchange_rate"
    ) {
        composable("exchange_rate") {
            ExchangeRateScreen(
                onNavigateToAlerts = {
                    navController.navigate("alerts")
                }
            )
        }
        
        composable("alerts") {
            AlertScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}