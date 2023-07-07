package com.machado001.hangman

import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createComposeRule
import com.machado001.hangman.ui.screens.gameScreen.GameScreen

import org.junit.Test
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GameScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun gameScreen_ContentIsDisplayed() {
        composeTestRule.setContent {
            GameScreen(onNavigateUp = {})
        }
        composeTestRule.onNode(hasAnyChild(isRoot()))
    }
}

