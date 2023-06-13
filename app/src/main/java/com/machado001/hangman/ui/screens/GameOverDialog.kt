package com.machado001.hangman.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun GameOverDialog(
    resetGame: () -> Unit,
    correctLetters: Set<Char>,
    wrongLetters: Set<Char>,
    wordChosen: String
) {
    AlertDialog(
        title = { Text(text = "GAME OVER!") },
        text = { DialogContentColumn(correctLetters, wrongLetters, wordChosen) },
        onDismissRequest = { resetGame() },
        confirmButton = {
            Button(
                onClick = { resetGame() },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Jogar de novo")
            }
        },
        dismissButton = {
            Button(
                onClick = {},
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Sair")
            }
        },
    )
}

@Composable
private fun DialogContentColumn(
    correctLetters: Set<Char>,
    wrongLetters: Set<Char>,
    wordChosen: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        RevealWordRow(wordChosen)
        LettersRow(letters = correctLetters, color = Color.Green)
        LettersRow(letters = wrongLetters, color = Color.Red)
    }
}

@Composable
private fun LettersRow(
    modifier: Modifier = Modifier,
    letters: Set<Char>,
    color: Color
) {
    Row {
        Text(text = "Letters:", color = color)
        letters.forEach { letter ->
            Text(text = letter.toString().uppercase())
            Spacer(modifier = Modifier.padding(end = 4.dp))
        }
    }
}

@Composable
private fun RevealWordRow(wordChosen: String, modifier: Modifier = Modifier) {
    Row() {
        Text(text = "the word was: $wordChosen")
    }
}
