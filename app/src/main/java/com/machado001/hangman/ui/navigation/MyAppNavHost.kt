package com.machado001.hangman.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.machado001.hangman.ui.screens.GameScreen
import com.machado001.hangman.ui.screens.Home
import com.machado001.hangman.ui.screens.SettingsScreen

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("home") {
            Home(
                modifier = modifier,
                onNavigateToGame = { navController.navigate("game") },
                onNavigateToSettings = {navController.navigate("settings")}
            )
        }
        composable("game") {
            GameScreen()
        }
        composable("settings") {
            SettingsScreen()
        }
    }
}