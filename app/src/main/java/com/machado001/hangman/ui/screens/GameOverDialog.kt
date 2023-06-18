package com.machado001.hangman.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.machado001.hangman.R
import com.machado001.hangman.ui.theme.HangmanTheme


@Composable
fun GameOverDialog(
    resetGame: () -> Unit,
    wordChosen: String,
) {
    AlertDialog(
        title = { GameOverText() },
        text = {
            DialogContentColumn(wordChosen = wordChosen)
        },
        onDismissRequest = { resetGame() },
        confirmButton = {
            Button(
                onClick = { resetGame() },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = stringResource(id = R.string.PLAY_AGAIN_BUTTON_DIALOG))
            }
        },
        dismissButton = {
            Button(
                onClick = {},
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = stringResource(id = R.string.QUIT_GAME_BUTTON_DIALOG))
            }
        },
    )
}

@Composable
private fun GameOverText() {
    Text(
        text = "GAME OVER!",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun DialogContentColumn(
    wordChosen: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        RevealedWordRow(wordChosen)
    }
}


@Composable
private fun RevealedWordRow(
    wordChosen: String,
    modifier: Modifier = Modifier
) {
    Row {
        Text(text = "the word was:\n$wordChosen")
    }
}

@Preview
@Composable
fun GameOverDialogPreview() {
    HangmanTheme {
        GameOverDialog(
            resetGame = { /*TODO*/ },
            wordChosen = "ABOCADO",
        )
    }
}

