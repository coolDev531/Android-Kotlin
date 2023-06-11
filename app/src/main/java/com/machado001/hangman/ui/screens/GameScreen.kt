package com.machado001.hangman.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.machado001.hangman.ui.theme.HangmanTheme

@Composable
fun GameScreen(
    gameViewModel: GameScreenViewModel = viewModel()
) {
    val gameUiState by gameViewModel.uiState.collectAsState()

    LaunchedEffect(gameViewModel.uiState) {
        gameViewModel.pickRandomWord()
    }

    GameContent(
        wrongLetters = gameUiState.wrongLetters,
        wordChosen = gameUiState.wordRandomlyChosen,
        correctLetters = gameUiState.correctLetters,
        livesCount = gameUiState.livesLeft,
        checkUserGuess = { gameViewModel.checkUserGuess(it) },
        resetGame = { gameViewModel.resetGame() },
        isGameOver = gameViewModel.isGameOver,
        usedLetters = gameUiState.usedLetters
    )

}


@Composable
private fun GameContent(
    wordChosen: String,
    correctLetters: Set<Char>,
    wrongLetters: Set<Char>,
    livesCount: Int,
    checkUserGuess: (Char) -> Unit,
    resetGame: () -> Unit,
    isGameOver: Boolean,
    usedLetters: Set<Char>
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            LivesLeftRow(livesCount = livesCount)
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.Center,
            state = LazyListState(firstVisibleItemIndex = 1)
        ) {
            items(
                wordChosen.length,
                key = { it }
            ) { index ->
                LetterFromWord(wordChosen[index], correctLetters)
            }
        }
        KeyboardLayout(
            alphabetList = alphabetSet.toList(),
            checkUserGuess = { checkUserGuess(it) },
            correctLetters = correctLetters,
            usedLetters = usedLetters,
            wrongLetters = wrongLetters
        )
    }

    if (isGameOver) {
        GameOverDialog(resetGame = resetGame, correctLetters, wrongLetters, wordChosen)
    }
}


@Composable
@Stable
private fun LetterFromWord(
    letter: Char,
    correctLetters: Set<Char>
) {

    Box(
        modifier = Modifier
            .defaultMinSize(40.dp)
            .padding(end = 8.dp)
            .background(color = MaterialTheme.colorScheme.surface)
            .drawWithContent {
                drawContent()
                drawLine(
                    color = Color.Black,
                    start = Offset(1f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 4.dp.toPx()
                )
            },
        contentAlignment = Alignment.Center
    ) {
        key(letter) {
            Text(
                text = letter.toString().uppercase(),
                modifier = Modifier
                    .padding(4.dp)
                    .alpha(
                        if (correctLetters.contains(letter)) 1f else 0f
                    ),
                color = MaterialTheme.colorScheme.inversePrimary,
                fontSize = 24.sp,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}


@Composable
private fun LivesLeftRow(
    livesCount: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Lives remaining: ".uppercase(),
            style = MaterialTheme.typography.labelMedium
        )
        repeat(livesCount) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Lives Player Count",
                tint = Color.Red,
                modifier = Modifier
            )
        }
    }
}


@Composable
private fun KeyboardLayout(
    checkUserGuess: (Char) -> Unit,
    alphabetList: List<Char>,
    correctLetters: Set<Char>,
    usedLetters: Set<Char>,
    wrongLetters: Set<Char>
) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(40.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

        ) {
        items(alphabetList.size) {
            val keyLetter = alphabetList[it]
            KeyboardKey(
                letterFromButton = keyLetter,
                correctLetters = correctLetters,
                usedLetters = usedLetters,
                wrongLetters = wrongLetters
            ) {
                checkUserGuess(keyLetter)
            }
        }
    }
}


@Composable
private fun KeyboardKey(
    modifier: Modifier = Modifier,
    letterFromButton: Char,
    correctLetters: Set<Char>,
    wrongLetters: Set<Char>,
    usedLetters: Set<Char>,
    checkUserGuess: (Char) -> Unit
) {

    val isEnabled = !usedLetters.contains(letterFromButton)


    val checkCorrectness = correctLetters.contains(letterFromButton.lowercaseChar())
    TextButton(
        onClick = {
            checkUserGuess(letterFromButton)
        },
        enabled = isEnabled,
        shape = RoundedCornerShape(percent = 50),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
            contentColor = Color.White,
            disabledContainerColor = if (checkCorrectness) Color.Green else Color.Red,
            disabledContentColor = if (checkCorrectness) Color.Black else Color.Yellow
        ),
        modifier = modifier
            .padding(4.dp)
            .alpha(if (isEnabled) 1f else 0.12f),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = letterFromButton.toString(),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}


@Preview
@Composable
fun Sexo() {
    HangmanTheme(darkTheme = true) {
        GameScreen()
    }
}


