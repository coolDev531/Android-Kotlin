package com.machado001.hangman.ui.screens

import androidx.lifecycle.ViewModel
import com.machado001.hangman.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState = _uiState.asStateFlow()

    private var lettersGuessed: MutableSet<Char> = mutableSetOf()
    private var correctLetters: MutableSet<Char> = mutableSetOf()
    private var wrongLetters: MutableSet<Char> = mutableSetOf()

    private var _currentLetterGuessed: Char = ' '

    private var _isGameOver: Boolean = false
    val isGameOver: Boolean
        get() = _uiState.value.livesLeft == 0


    fun pickRandomWord() {
        val currentWord = allWords.random()
        _uiState.update { currentState ->
            currentState.copy(wordRandomlyChosen = currentWord)
        }
    }

    private fun isGuessCorrect(letterFromButton: Char): Boolean {
        return _uiState.value.wordRandomlyChosen.contains(
            char = letterFromButton,
            ignoreCase = true
        )
    }

    fun checkUserGuess(letterFromButton: Char) {
        lettersGuessed.add(letterFromButton)
        _currentLetterGuessed = letterFromButton.lowercaseChar()

        if (isGuessCorrect(letterFromButton)) {
            correctLetters.add(_currentLetterGuessed)

            _uiState.update { currentState ->
                currentState.copy(
                    usedLetters = lettersGuessed.toSet(),
                    correctLetters = correctLetters.toSet(),
                )
            }
        } else {
            wrongLetters.add(_currentLetterGuessed)

            _uiState.update { currentState ->
                currentState.copy(
                    usedLetters = lettersGuessed.toSet(),
                    wrongLetters = wrongLetters.toSet(),
                    livesLeft = currentState.livesLeft.dec(),
                )
            }
        }
    }

    fun resetGame() {
        lettersGuessed.clear()
        correctLetters.clear()
        wrongLetters.clear()
        _currentLetterGuessed = '_'
        _isGameOver = false
        _uiState.value = GameUiState()
        pickRandomWord()// Reset the entire UI state object
    }

}
