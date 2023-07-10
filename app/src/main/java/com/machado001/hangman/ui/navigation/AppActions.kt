package com.machado001.hangman.ui.navigation

import androidx.navigation.NavHostController
import com.machado001.hangman.ui.navigation.AppDestinations.GAME_SCREEN_ROUTE
import com.machado001.hangman.ui.navigation.AppDestinations.INSTRUCTIONS_SCREEN_ROUTE

class AppActions(
    private val navController: NavHostController,
    private val routes: AppDestinations
) {
    val navigateToGameScreen = {
        navController.navigate(routes.GAME_SCREEN_ROUTE)
    }

    val navigateToInstructionsScreen = {
        navController.navigate(routes.INSTRUCTIONS_SCREEN_ROUTE)
    }

    fun finishActivity(finishActivity: Unit) = finishActivity

    // Navigates to previous screen from current screen.
    val navigateUp: () -> Unit = { navController.navigateUp() }

    val popBack: () -> Boolean = { navController.popBackStack() }
}