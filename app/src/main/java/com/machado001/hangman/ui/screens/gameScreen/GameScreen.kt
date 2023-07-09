package com.machado001.hangman.ui.screens.gameScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
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
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.machado001.hangman.R
import com.machado001.hangman.ui.components.dialogs.BackToHomeDialog
import com.machado001.hangman.ui.components.dialogs.TipDialog
import com.machado001.hangman.ui.components.dialogs.GameOverDialog
import com.machado001.hangman.ui.theme.HangmanTheme
import java.text.Normalizer

@Composable
fun GameScreen(
    gameViewModel: GameScreenViewModel = viewModel(),
    onNavigateUp: () -> Unit,
    onPopBack: () -> Boolean
) {
    val gameUiState by gameViewModel.uiState.collectAsState()

    GameContent(
        onPopBack = onPopBack,
        onNavigateUp = onNavigateUp,
        wordChosen = gameUiState.wordRandomlyChosen,
        correctLetters = gameUiState.correctLetters,
        livesCount = gameUiState.livesLeft,
        checkUserGuess = { gameViewModel.checkUserGuess(it) },
        resetGame = { gameViewModel.resetStates() },
        isGameOver = gameViewModel.isGameOver,
        usedLetters = gameUiState.usedLetters,
        category = gameUiState.categoryRandomlyChosen,
        winCount = gameUiState.streakCount,
        isWordCorrectlyGuessed = {
            gameViewModel.isWordCorrectlyGuessed(
                gameUiState.wordRandomlyChosen,
                gameUiState.correctLetters
            )
        }
    )
}

@Composable
private fun GameContent(
    wordChosen: String?,
    correctLetters: Set<Char>,
    livesCount: Int,
    checkUserGuess: (Char) -> Unit,
    resetGame: () -> Unit,
    isGameOver: Boolean,
    usedLetters: Set<Char>,
    category: String,
    winCount: Int,
    onNavigateUp: () -> Unit,
    isWordCorrectlyGuessed: () -> Boolean,
    onPopBack: () -> Boolean
) {

    if (isWordCorrectlyGuessed()) {
        resetGame()
    }
    if (isGameOver) {
        GameOverDialog(
            resetGame = resetGame,
            wordChosen = wordChosen,
            hitsCount = winCount
        )
    }
    BackToHomeDialog(onNavigateUp)
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
    ) {
        TopAppBarRow(modifier = Modifier, onNavigateUp, onPopBack)
        LivesLeftRow(
            livesCount
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            ChosenWordRow(
                wordChosen,
                correctLetters
            )
            TipAndCountTextRow(tip = category, winCount)
            KeyboardLayout(
                alphabetList = alphabetSet.toList(),
                checkUserGuess = { checkUserGuess(it) },
                correctLetters = correctLetters,
                usedLetters = usedLetters,
            )
        }
    }
}

@Composable
fun materialColor(): Color {
    return MaterialTheme.colorScheme.onSurface
}


@Composable
private fun ChosenWordRow(
    wordChosen: String?,
    correctLetters: Set<Char>,
) {
    println("PENIS $wordChosen")

    val materialColor = remember { mutableStateOf(Color.Transparent) }
    materialColor.value = materialColor()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        LazyRow {
            items(wordChosen!!.length) { letterIndex ->
                val currentChar = wordChosen[letterIndex]
                if (currentChar.isWhitespace()) {
                    Spacer(modifier = Modifier.padding(16.dp))

                } else if (currentChar == '-') {
                    Text(
                        modifier = Modifier
                            .padding(1.2.dp)
                            .size(32.dp),
                        text = "-",
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    WordLetter(
                        letter = currentChar,
                        correctLetters = correctLetters
                    )
                }
            }
        }
    }
}


@Composable
@Stable
private fun WordLetter(
    modifier: Modifier = Modifier,
    letter: Char,
    correctLetters: Set<Char> = emptySet()
) {

    val letterNormalized = Normalizer.normalize(letter.toString(), Normalizer.Form.NFD)
    val normalizedChar: Char = letterNormalized[0]
    val isLetterCorrect: Boolean = correctLetters.contains(normalizedChar)
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
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = cardColors(
            containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
            contentColor = MaterialTheme.colorScheme.surface
        )
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
fun TipAndCountTextRow(tip: String, winCount: Int) {
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = stringResource(id = R.string.TIP_TEXT, tip))
        AnimatedWinCount(winCount = winCount)
    }
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
private fun AnimatedWinCount(winCount: Int) {
    AnimatedContent(
        targetState = winCount,
        transitionSpec = {
            // Compare the incoming number with the previous number.
            if (targetState > initialState) {
                // If the target number is larger, it slides up and fades in
                // while the initial (smaller) number slides up and fades out.
                slideInVertically { height -> height } + fadeIn() with
                        slideOutVertically { height -> -height } + fadeOut()
            } else {
                // If the target number is smaller, it slides down and fades in
                // while the initial number slides down and fades out.
                slideInVertically { height -> -height } + fadeIn() with
                        slideOutVertically { height -> height } + fadeOut()
            }.using(
                // Disable clipping since the faded slide-in/out should
                // be displayed out of bounds.
                SizeTransform(clip = false)
            )
        }
    ) { targetCount ->
        Text(text = stringResource(id = R.string.WIN_COUNT_TEXT, targetCount))
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


@Composable
fun IconRow(
    modifier: Modifier = Modifier,
    OrientationInsideRow: Arrangement.Horizontal,
    onClick: () -> Unit,
    imageVector: ImageVector,
    IconContentDescription: String? = null
) {
    Row(
        modifier = modifier,
        horizontalArrangement = OrientationInsideRow
    ) {
        IconButton(
            onClick = onClick
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
fun TopAppBarRow(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    onPopBack: () -> Boolean
) {
    var showActionFromBackIcon by rememberSaveable {
        mutableStateOf(false)
    }
    var showActionFromInfoIcon by rememberSaveable {
        mutableStateOf(false)
    }

    if (showActionFromInfoIcon) {
        TipDialog {
            showActionFromInfoIcon = false
        }
    }


    Row(modifier = modifier.fillMaxWidth()) {
        val modifierWeightValue = modifier
            .weight(0.5f)
        IconRow(
            modifier = modifierWeightValue,
            OrientationInsideRow = Arrangement.Start,
            onClick = { onPopBack() },
            imageVector = Icons.Rounded.ArrowBack,
        )
        IconRow(
            modifier = modifierWeightValue,
            OrientationInsideRow = Arrangement.End,
            onClick = { showActionFromInfoIcon = true },
            imageVector = Icons.Rounded.Info,

            )
    }
}

@Preview(showBackground = true)
@Composable
fun BackIconButtonPreview() {
    HangmanTheme {
        TopAppBarRow(onNavigateUp = {}) { true }
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
            ButtonState.Correct -> MaterialTheme.colorScheme.secondary
            ButtonState.Incorrect -> MaterialTheme.colorScheme.error
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
    checkUserGuess: (Char) -> Unit,
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
            disabledContentColor = MaterialTheme.colorScheme.outline
        ),
        modifier = modifier
            .padding(4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Text(
            text = letterFromButton.toString(),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center,
        )
    }
}

