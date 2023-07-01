package com.machado001.hangman.ui.screens

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
        gameViewModel.pickRandomWordAndCategory()
    }

    GameContent(
        wordChosen = gameUiState.wordRandomlyChosen,
        correctLetters = gameUiState.correctLetters,
        livesCount = gameUiState.livesLeft,
        ignoredCheckUserGuess = { gameViewModel.checkUserGuess(it) },
        resetGame = { gameViewModel.resetStates() },
        isGameOver = gameViewModel.isGameOver,
        usedLetters = gameUiState.usedLetters,
        category = gameUiState.categoryRandomlyChosen,
        updateStreakCount = { gameViewModel.updateStreakCount(gameUiState.streakCount) }
    )
}


@Composable
private fun GameContent(
    wordChosen: String?,
    correctLetters: Set<Char>,
    livesCount: Int,
    ignoredCheckUserGuess: (Char) -> Unit,
    resetGame: () -> Unit,
    isGameOver: Boolean,
    usedLetters: Set<Char>,
    category: String,
    updateStreakCount: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly


    ) {

        LivesLeftRow(
            livesCount
        )

        Column {
            ChosenWordFlowRow(
                wordChosen,
                correctLetters
            )
            TipRow(tip = category)
            KeyboardLayout(
                alphabetList = alphabetSet.toList(),
                checkUserGuess = { ignoredCheckUserGuess(it) },
                correctLetters = correctLetters,
                usedLetters = usedLetters,
            )

        }

    }
    val isWordCorrectlyGuessed = wordChosen?.let { correctLetters.containsAll(it.toList()) }

    if (isGameOver) {
        GameOverDialog(
            resetGame = resetGame,
            wordChosen = wordChosen,
        )
    }

    if (isWordCorrectlyGuessed == true) {
        updateStreakCount()
    }
}

@Composable
fun materialColor(): Color {
    return MaterialTheme.colorScheme.onSurface
}


@Composable
private fun ChosenWordFlowRow(
    wordChosen: String?,
    correctLetters: Set<Char>
) {
    println("PENIS $wordChosen")

    val materialColor = remember { mutableStateOf(Color.Transparent) }
    materialColor.value = materialColor()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        LazyRow {
            items(wordChosen!!.length) { index ->
                WordLetter(
                    letter = wordChosen[index],
                    correctLetters = correctLetters
                )
            }
        }
    }
}


@Composable
@Stable
private fun WordLetter(
    modifier: Modifier = Modifier,
    letter: Char,
    correctLetters: Set<Char>
) {

    val isLetterCorrect: Boolean = correctLetters.contains(letter)
    val alphaValue = if (isLetterCorrect) ALPHA_CORRECT_VALUE else ALPHA_INCORRECT_VALUE
    val alphaAnimation: Float by animateFloatAsState(
        targetValue = alphaValue,
        animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
    )
    //alphaAnimation will occurs only if the word is correctly chosen. this line is necessary since their lack results in a bug.
    val haveAnimationOrNot = if (alphaValue == ALPHA_CORRECT_VALUE) alphaAnimation else alphaValue

    Card(
        modifier = modifier
            .padding(2.4.dp)
            .size(32.dp),
        elevation = CardDefaults.elevatedCardElevation(),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .alpha(haveAnimationOrNot),
            text = letter.toString().uppercase(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
    }
}

@Composable
fun TipRow(tip: String = "Some_tip_here") {
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Tip: $tip")
    }
}

private const val ALPHA_CORRECT_VALUE = 1F

private const val ALPHA_INCORRECT_VALUE = 0F


@Composable
private fun LivesLeftRow(
    livesCount: Int
) {
    val infiniteTransition = rememberInfiniteTransition()
    val pulsate by infiniteTransition.animateFloat(
        initialValue = 35f,
        targetValue = 37f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 4000
                35f at 0 with LinearOutSlowInEasing
                37f at 200 with LinearOutSlowInEasing
                35f at 400 with LinearOutSlowInEasing
                37f at 600 with LinearOutSlowInEasing
                35f at 800 with LinearOutSlowInEasing
                35f at 4000 with LinearEasing
            },
            repeatMode = RepeatMode.Restart
        ),

        )
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .requiredHeight(38.dp) //this is necessary because the heart's animation is affecting the other composable elements
            .fillMaxWidth()
    ) {
        repeat(livesCount) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Lives Player Count: $livesCount",
                tint = Color.Red,
                modifier = Modifier
                    .size(pulsate.dp)
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

enum class ButtonState { Correct, Incorrect }

private class ButtonTransitionData(
    color: State<Color>
) {
    val color by color
}

@Composable
private fun updateButtonTransitionData(
    checkCorrectness: Boolean
): ButtonTransitionData {

    val targetState = when (checkCorrectness) {
        true -> ButtonState.Correct
        false -> ButtonState.Incorrect
    }
    val transition = updateTransition(
        targetState = targetState,
        label = "button state"
    )
    val color = transition.animateColor(
        label = "color"
    ) { state ->
        when (state) {
            ButtonState.Correct -> MaterialTheme.colorScheme.onPrimary
            ButtonState.Incorrect -> MaterialTheme.colorScheme.onError
        }
    }
    return remember(transition) { ButtonTransitionData(color) }
}

@Composable
private fun KeyboardKey(
    modifier: Modifier = Modifier,
    letterFromButton: Char,
    correctLetters: Set<Char>,
    usedLetters: Set<Char>,
    checkUserGuess: (Char) -> Unit
) {
    val isEnabled = remember(
        letterFromButton,
        usedLetters
    ) { !usedLetters.contains(letterFromButton) }
    val checkCorrectness = remember(
        letterFromButton,
        correctLetters
    ) { correctLetters.contains(letterFromButton.lowercaseChar()) }

    val transitionData = updateButtonTransitionData(checkCorrectness = checkCorrectness)

    TextButton(
        onClick = {
            checkUserGuess(letterFromButton)
        },
        enabled = isEnabled,
        shape = ShapeDefaults.ExtraLarge,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = transitionData.color,
        ),
        modifier = modifier
            .padding(4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Text(
            text = letterFromButton.toString(),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.fillMaxSize(),
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


