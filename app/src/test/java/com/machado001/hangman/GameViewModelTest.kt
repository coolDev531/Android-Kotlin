package com.machado001.hangman

import com.machado001.hangman.ui.screens.GameScreenViewModel
import com.machado001.hangman.ui.screens.alphabetSet
import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GameScreenViewModelTest {
    private val viewModel = GameScreenViewModel()


    @Test
    fun gameScreenViewModel_PickRandomWord_WordChosenIsNotEmpty() {
        viewModel.pickRandomWord()
        val wordChosen = viewModel.uiState.value.wordRandomlyChosen
        assertTrue(wordChosen.isNotEmpty())
    }

    @Test
    fun gameScreenViewModel_CorrectWordGuessed_CorrectLettersUpdated() {
        viewModel.pickRandomWord()
        val wordChosen = viewModel.uiState.value.wordRandomlyChosen
        val letter = wordChosen.first()
        viewModel.checkUserGuess(letter)
        val correctLetters = viewModel.uiState.value.correctLetters
        assertTrue(correctLetters.contains(letter))
    }

    @Test
    fun gameScreenViewModel_WrongWordGuessed_WrongLettersUpdated() {
        viewModel.pickRandomWord()
        viewModel.uiState.value.wordRandomlyChosen
        val letter = 'z'
        viewModel.checkUserGuess(letter)
        val wrongLetters = viewModel.uiState.value.wrongLetters
        assertTrue(wrongLetters.contains(letter))
    }

    @Test
    fun gameScreenViewModel_GameOver_LivesLeftIsZero() {
        viewModel.pickRandomWord()
        val wordChosen = viewModel.uiState.value.wordRandomlyChosen

        val incorrectLetters = alphabetSet.filter { !wordChosen.contains(it, ignoreCase = true) }

        var currentLives = viewModel.uiState.value.livesLeft
        var index = 0

        while (currentLives > 0) {
            val letter = incorrectLetters.getOrNull(index)
            if (letter != null) {
                viewModel.checkUserGuess(letter)
                currentLives = viewModel.uiState.value.livesLeft
            }
            index++
        }

        val livesLeft = viewModel.uiState.value.livesLeft
        assertEquals(0, livesLeft)
    }

    @Test
    fun gameScreenViewModel_ResetGame_NewGameStarted() {
        val livesLeft = viewModel.uiState.value.livesLeft
        viewModel.pickRandomWord()
        viewModel.checkUserGuess('A')
        viewModel.resetGame()
        val uiState = viewModel.uiState.value
        println(uiState)
        assertTrue(uiState.wordRandomlyChosen.isNotEmpty())
        assertTrue(uiState.usedLetters.isEmpty())
        assertTrue(uiState.correctLetters.isEmpty())
        assertTrue(uiState.wrongLetters.isEmpty())
        assertTrue(uiState.selectedLetters.isEmpty())
        assertEquals(livesLeft, uiState.livesLeft)
        assertFalse(uiState.isGameOver)
    }
}