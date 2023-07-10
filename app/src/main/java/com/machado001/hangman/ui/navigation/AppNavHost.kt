package com.machado001.hangman.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.machado001.hangman.MainActivity
import com.machado001.hangman.ui.navigation.AppDestinations.GAME_SCREEN_ROUTE
import com.machado001.hangman.ui.navigation.AppDestinations.HOME_SCREEN_ROUTE
import com.machado001.hangman.ui.navigation.AppDestinations.INSTRUCTIONS_SCREEN_ROUTE
import com.machado001.hangman.ui.screens.gameScreen.GameScreen
import com.machado001.hangman.ui.screens.homeScreen.Home
import com.machado001.hangman.ui.screens.instructionsScreen.InstructionsScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HOME_SCREEN_ROUTE,
    routes: AppDestinations = AppDestinations,
    activity: MainActivity = MainActivity()
) {
    val actions = remember(navController) {
        AppActions(navController, routes)
    }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(startDestination) {
            Home(
                modifier = modifier,
                onNavigateToGame = actions.navigateToGameScreen,
                onNavigateToInstructions = actions.navigateToInstructionsScreen,
                onNavigateUp = { actions.finishActivity(activity.finish()) },
            )
        }
        composable(GAME_SCREEN_ROUTE) {
            GameScreen(
                onNavigateUp = actions.navigateUp,
                onPopBack = actions.popBack
            )
        }
        composable(INSTRUCTIONS_SCREEN_ROUTE) {
            InstructionsScreen(onNavigateUp = actions.navigateUp)
        }
    }
}