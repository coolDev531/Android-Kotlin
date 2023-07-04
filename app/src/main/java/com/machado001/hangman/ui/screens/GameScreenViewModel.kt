package com.machado001.hangman.ui.screens

import androidx.lifecycle.ViewModel
import com.machado001.hangman.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.Collator


class GameScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState = _uiState.asStateFlow()

    private var lettersGuessed: MutableSet<Char> = mutableSetOf()
    private var correctLetters: MutableSet<Char> = mutableSetOf()
    private var wrongLetters: MutableSet<Char> = mutableSetOf()
    private var word: String? = ""

    private var _currentLetterGuessed: Char = ' '

    private var _isGameOver: Boolean = false
    val isGameOver: Boolean get() = _uiState.value.livesLeft == 0

    private var currentStreakCount: Int = 0


    fun pickRandomWordAndCategory() {
        val currentCategory = allWords.keys.random()
        val currentWord = allWords[currentCategory]?.random()
        word = currentWord
        _uiState.update { currentState ->
            currentState.copy(
                wordRandomlyChosen = word,
                categoryRandomlyChosen = currentCategory
            )
        }
    }


    private fun isLetterGuessCorrect(letterFromButton: Char): Boolean? {
        val collator: Collator = Collator.getInstance()
        collator.strength = Collator.PRIMARY

        val wordRandomlyChosen = _uiState.value.wordRandomlyChosen
        val normalizedLetterFromButton = letterFromButton.toString()

        return wordRandomlyChosen?.any { char ->
            collator.compare(char.toString(), normalizedLetterFromButton) == 0
        }
    }

    fun checkUserGuess(letterFromButton: Char) {
        lettersGuessed.add(letterFromButton)
        _currentLetterGuessed = letterFromButton.lowercaseChar()

        if (isLetterGuessCorrect(letterFromButton) == true) {
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

    fun resetStates() {
        val currentStreakCount = _uiState.value.streakCount // Salva o valor atual do streakCount
        _uiState.value =
            GameUiState(streakCount = currentStreakCount) // Cria um novo GameUiState com o valor do streakCount preservado
        lettersGuessed.clear()
        correctLetters.clear()
        wrongLetters.clear()
        _currentLetterGuessed = ' '
        _isGameOver = false
        pickRandomWordAndCategory()
    }

    fun updateStreakCount(streakCount: Int) {
        resetStates()
        _uiState.update { currentState ->
            currentState.copy(
                streakCount = streakCount.inc()
            )
        }
    }
}
