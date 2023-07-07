package com.machado001.hangman.ui.screens.homeScreen


import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.machado001.hangman.R
import com.machado001.hangman.ui.theme.HangmanTheme


@Composable
fun Home(
    modifier: Modifier = Modifier,
    onNavigateToGame: () -> Unit = {},
    onNavigateToInstructions: () -> Unit = {}
) {

    Box(modifier = modifier.fillMaxSize()) {
        CenterDiv(
            modifier = modifier
                .align(Alignment.Center),
            onNavigateToGame = onNavigateToGame,
            onNavigateToInstructions = onNavigateToInstructions,
        )
    }
}

@Composable
fun CenterDiv(
    modifier: Modifier = Modifier,
    onNavigateToGame: () -> Unit,
    onNavigateToInstructions: () -> Unit,
) {
    val paddingValue = 8.dp
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = ""
        )
        Text(
            text = stringResource(id = R.string.APP_NAME).uppercase(),
            style = MaterialTheme.typography.headlineLarge
        )
        Column(
            modifier = modifier
                .padding(paddingValue)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
            ) {
                ButtonToNavigate(
                    onNavigateToGame,
                    modifier,
                    paddingValue,
                    R.string.PLAY_BUTTON
                )
                ButtonToNavigate(
                    onNavigateToInstructions,
                    modifier,
                    paddingValue,
                    R.string.INSTRUCTIONS_TITLE
                )
            }
        }
    }
}

@Composable
private fun ButtonToNavigate(
    onNavigateTo: () -> Unit,
    modifier: Modifier,
    paddingValue: Dp,
    @StringRes stringResourceId: Int
) {
    Row {
        Button(
            onClick = onNavigateTo,
            modifier = modifier
                .fillMaxWidth()
                .padding(end = paddingValue),
            elevation = ButtonDefaults.buttonElevation(paddingValue)
        ) {
            UppercaseText(
                text = stringResource(id = stringResourceId),
            )
        }
    }
}

@Composable
fun UppercaseText(text: String) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HangmanTheme {
        Home { }
    }
}