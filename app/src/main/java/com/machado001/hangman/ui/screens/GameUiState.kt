package com.machado001.hangman.ui.screens


data class GameUiState(
    val wordRandomlyChosen: String = "",
    val usedLetters: Set<Char> = emptySet(),
    val correctLetters: Set<Char> = emptySet(),
    val wrongLetters: Set<Char> = emptySet(),
    val selectedLetters: Set<Char> = emptySet(),
    val livesLeft: Int = 4,
    val isGameOver: Boolean = false
)

val alphabetSet = "QWERTYUIOPASDFGHJKLZXCVBNM".toSet()


