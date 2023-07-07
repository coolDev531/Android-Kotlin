package com.machado001.hangman.ui.screens.gameScreen


data class GameUiState(
    val wordRandomlyChosen: String? = "",
    val categoryRandomlyChosen: String = "",
    val usedLetters: Set<Char> = emptySet(),
    val correctLetters: Set<Char> = emptySet(),
    val wrongLetters: Set<Char> = emptySet(),
    val livesLeft: Int = 5,
    val isGameOver: Boolean = false,
    val streakCount: Int = 0
)

val alphabetSet = ('A'..'Z').toSet()


