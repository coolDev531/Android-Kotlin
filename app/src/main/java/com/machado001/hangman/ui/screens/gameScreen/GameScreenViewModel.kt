package com.machado001.hangman.ui.screens.gameScreen

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
    val isGameOver: Boolean get() = _uiState.value.livesLeft == 0

    private var _currentStreakCount: Int = 0

    init {
        pickRandomWordAndCategory()
    }

    fun pickRandomWordAndCategory() {
        val currentCategory = allWords.keys.random()
        val currentWord = allWords[currentCategory]?.random()
        word = currentWord?.lowercase()
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
        _currentStreakCount = if (!isGameOver) ++_currentStreakCount else 0
        _uiState.value = GameUiState(streakCount = _currentStreakCount)
        lettersGuessed.clear()
        correctLetters.clear()
        wrongLetters.clear()
        _currentLetterGuessed = ' '
        pickRandomWordAndCategory()
    }
}






