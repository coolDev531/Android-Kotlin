package com.machado001.hangman.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.machado001.hangman.R
import com.machado001.hangman.ui.theme.HangmanTheme


@Composable
fun Home(
    modifier: Modifier = Modifier,
    onNavigateToGame: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {}
) {

    Box(modifier = modifier.fillMaxSize()) {
        CenterDiv(
            modifier = modifier
                .align(Alignment.Center),
            onNavigateToGame = onNavigateToGame,
            onNavigateToSettings = onNavigateToSettings
        )
    }
}

@Composable
fun CenterDiv(
    modifier: Modifier = Modifier,
    onNavigateToGame: () -> Unit,
    onNavigateToSettings: () -> Unit = {}
) {
    val paddingValue = 16.dp
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
        Row(
            modifier = modifier
                .padding(paddingValue)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    onNavigateToGame()
                },
                modifier = modifier
                    .padding(end = paddingValue),
                elevation = ButtonDefaults.buttonElevation(paddingValue)
            ) {
                UppercaseText(text = stringResource(id = R.string.PLAY_BUTTON))
            }
            OutlinedButton(
                onClick = { onNavigateToSettings() },
                modifier = modifier.weight(0.5f)
            ) {
                UppercaseText(text = stringResource(id = R.string.SETTINGS_BUTTON))
            }
        }
    }
}

@Composable
fun UppercaseText(text: String, ) {
    Text(text = text.uppercase())
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HangmanTheme {
        Home()
    }
}