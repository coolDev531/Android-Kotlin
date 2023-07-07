package com.machado001.hangman.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.machado001.hangman.ui.screens.gameScreen.GameScreen
import com.machado001.hangman.ui.screens.homeScreen.Home
import com.machado001.hangman.ui.screens.instructionsScreen.InstructionsScreen

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestinations.Home.name
) {
    val gameRoute = AppDestinations.Game.name
    val instructionsRoute = AppDestinations.Instructions.name
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(startDestination) {
            Home(
                modifier = modifier,
                onNavigateToGame = { navController.navigate(gameRoute) },
                onNavigateToInstructions = { navController.navigate(instructionsRoute) },
            )
        }
        composable(gameRoute) {
            GameScreen(onNavigateUp = { navController.navigateUp() })
        }
        composable(instructionsRoute) {
            InstructionsScreen(onNavigateUp = { navController.navigateUp() })
        }
    }
}