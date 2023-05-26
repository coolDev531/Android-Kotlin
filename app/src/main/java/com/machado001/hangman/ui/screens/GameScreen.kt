package com.machado001.hangman.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import com.machado001.hangman.data.alphabetSet
import com.machado001.hangman.ui.theme.HangmanTheme

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameScreenViewModel = viewModel()
) {
    val gameUiState by gameViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        gameViewModel.pickRandomWord()
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),

            horizontalArrangement = Arrangement.Center
        ) {
            for (letterToGuess in gameUiState.wordRandomlyChosen) {
                UniqueLetter(letterToGuess = letterToGuess, modifier = modifier)
            }
        }
        KeyboardLayout()
    }

}

@Composable
private fun UniqueLetter(
    letterToGuess: Char,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .defaultMinSize(40.dp)
            .padding(end = 8.dp)
            .background(color = MaterialTheme.colorScheme.surface)
            .drawWithContent {
                drawContent()
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 4.dp.toPx()
                )
            }
    ) {
        Text(
            text = letterToGuess.toString().uppercase(),
            modifier = Modifier
                .padding(4.dp)
                .alpha(0f),
            color = MaterialTheme.colorScheme.scrim,
            fontSize = 24.sp
        )
    }
}


@Composable
private fun KeyboardLayout() {
    val alphabetList = alphabetSet.toList().shuffled()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(40.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

    ) {
        items(alphabetList.size) {
            KeyboardKey(letter = alphabetList[it].toString())
        }
    }
}


@Composable
private fun KeyboardKey(
    letter: String,
    modifier: Modifier = Modifier,
    checkUserGuess: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Blue,
            contentColor = Color.White),
        modifier = modifier
            .padding(4.dp),
    ) {
            Text(
                text = letter,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxSize()
                    .clickable{

                    },

            )
    }
}


@Preview
@Composable
fun Sexo() {
    HangmanTheme(darkTheme = true) {
        GameScreen()
    }
}



