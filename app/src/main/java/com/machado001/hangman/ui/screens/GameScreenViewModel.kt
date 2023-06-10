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


   private var _currentLetterGuessed: Char = '_'

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

        if (isGuessCorrect(letterFromButton)) {
            _currentLetterGuessed = letterFromButton.lowercaseChar()

            correctLetters.add(_currentLetterGuessed)
            _uiState.update { currentState ->
                currentState.copy(
                    usedLetters = lettersGuessed.toSet(),
                    correctLetters = correctLetters.toSet(),
                )
            }
        } else {
            wrongLetters.add(letterFromButton)
            _uiState.update { currentState ->
                currentState.copy(
                    usedLetters = lettersGuessed.toSet(),
                    wrongLetters = wrongLetters.toSet(),
                    livesLeft = currentState.livesLeft.dec(),
                )
            }
        }
    }
}
