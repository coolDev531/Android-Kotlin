package com.machado001.hangman.ui.screens

import androidx.lifecycle.ViewModel
import com.machado001.hangman.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState = _uiState.asStateFlow()

    init {
        pickRandomWord()
    }



fun pickRandomWord() {
    val currentWord = allWords.random()
    _uiState.value = GameUiState(wordRandomlyChosen = currentWord)
}
}