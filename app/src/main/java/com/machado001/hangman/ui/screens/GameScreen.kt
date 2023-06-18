package com.machado001.hangman.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
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
        wordChosen = gameUiState.wordRandomlyChosen,
        correctLetters = gameUiState.correctLetters,
        livesCount = gameUiState.livesLeft,
        ignoredCheckUserGuess = { gameViewModel.checkUserGuess(it) },
        resetGame = { gameViewModel.resetStates() },
        isGameOver = gameViewModel.isGameOver,
        usedLetters = gameUiState.usedLetters,
        updateStreakCount = { gameViewModel.updateStreakCount(gameUiState.streakCount) }
    )
}


@Composable
private fun GameContent(
    wordChosen: String,
    correctLetters: Set<Char>,
    livesCount: Int,
    ignoredCheckUserGuess: (Char) -> Unit,
    resetGame: () -> Unit,
    isGameOver: Boolean,
    usedLetters: Set<Char>,
    updateStreakCount: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Center
            ) {
                LivesLeftRow(livesCount)
            }

        }
        Box {
            ChosenWordLazyRow(
                wordChosen,
                correctLetters
            )
        }
        Box {
            KeyboardLayout(
                alphabetList = alphabetSet.toList(),
                checkUserGuess = { ignoredCheckUserGuess(it) },
                correctLetters = correctLetters,
                usedLetters = usedLetters,
            )
        }
    }
    val isWordCorrectlyGuessed = correctLetters.containsAll(wordChosen.toList())

    if (isGameOver) {
        GameOverDialog(
            resetGame = resetGame,
            wordChosen = wordChosen,
        )
    }

    if (isWordCorrectlyGuessed) {
        updateStreakCount()
    }

}

@Composable
private fun ChosenWordLazyRow(
    wordChosen: String,
    correctLetters: Set<Char>
) {
    // This composable displays the chosen word in a LazyRow. Each letter is represented by a box.
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        items(
            wordChosen.length,
            contentType = { it },
            key = { it }
        ) { index ->
            WordLetter(
                modifier = letterFromWordBoxModifier,
                letter = wordChosen[index],
                correctLetters
            )
        }
    }
}

val letterFromWordBoxModifier = Modifier
    .padding(end = 2.4.dp)
    .drawWithContent {
        drawContent()
        drawLine(
            color = Color.Black,
            start = Offset(1f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = 4.dp.toPx()
        )
    }

// Constants
private const val ALPHA_CORRECT_VALUE = 1F
private const val LETTER_ALPHA_INCORRECT = 0F
private val LETTER_PADDING = 4.dp
private val LETTER_FONT_SIZE = 24.sp
private const val BUTTON_VISIBLE_FLOAT_VALUE = 1F
private const val BUTTON_TRANSLUCENT_FLOAT_VALUE = 0.48F

@Composable
@Stable
private fun WordLetter(
    modifier: Modifier = Modifier,
    letter: Char,
    correctLetters: Set<Char>
) {
    val isLetterCorrect = correctLetters.contains(letter)
    val alphaValue = if (isLetterCorrect) ALPHA_CORRECT_VALUE else LETTER_ALPHA_INCORRECT

    Box(
        modifier = modifier,
        contentAlignment = Center
    ) {
        Text(
            modifier = Modifier
                .padding(LETTER_PADDING)
                .alpha(alphaValue),
            text = letter.toString().uppercase(),
            color = MaterialTheme.colorScheme.inversePrimary,
            fontSize = LETTER_FONT_SIZE,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
private fun LivesLeftRow(livesCount: Int) {
    Row {
        repeat(livesCount) {
            val infiniteTransition = rememberInfiniteTransition()

            val pulsate by infiniteTransition.animateFloat(
                initialValue = 35f,
                targetValue = 37f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 4000
                        35f at 0 with LinearEasing
                        37f at 200 with LinearEasing
                        35f at 400 with LinearEasing
                        37f at 600 with LinearEasing
                        35f at 800 with LinearEasing
                        35f at 4000 with LinearEasing
                    },
                    repeatMode = RepeatMode.Restart
                ),
            )

            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Lives Player Count",
                tint = Color.Red,
                modifier = Modifier
                    .requiredSize(pulsate.dp)
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
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(40.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        items(alphabetList.size) {
            val keyLetter = alphabetList[it]
            key(it) {
                KeyboardKey(
                    letterFromButton = keyLetter,
                    correctLetters = correctLetters,
                    usedLetters = usedLetters,
                    checkUserGuess = { checkUserGuess(keyLetter) }
                )
            }
        }
    }
}


@Composable
private fun KeyboardKey(
    modifier: Modifier = Modifier,
    letterFromButton: Char,
    correctLetters: Set<Char>,
    usedLetters: Set<Char>,
    checkUserGuess: (Char) -> Unit
) {
    val isEnabled =
        remember(letterFromButton, usedLetters) { !usedLetters.contains(letterFromButton) }
    val checkCorrectness = remember(
        letterFromButton,
        correctLetters
    ) { correctLetters.contains(letterFromButton.lowercaseChar()) }
    TextButton(
        onClick = {
            checkUserGuess(letterFromButton)
        },
        enabled = isEnabled,
        shape = ShapeDefaults.ExtraLarge,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = if (checkCorrectness) Color.Green else Color.Red,
        ),
        modifier = modifier
            .alpha(if (isEnabled) BUTTON_VISIBLE_FLOAT_VALUE else BUTTON_TRANSLUCENT_FLOAT_VALUE)
            .padding(4.dp),
    ) {
        Text(
            text = letterFromButton.toString(),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }
}


@Preview
@Composable
fun GameScreenPreview() {
    HangmanTheme(darkTheme = true) {
        GameScreen()
    }
}


