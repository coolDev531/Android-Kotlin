package com.machado001.hangman.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.machado001.hangman.R
import com.machado001.hangman.ui.theme.HangmanTheme


@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("home") {
            Home(
                onNavigateToGame = { navController.navigate("game") },
                onNavigateToSettings = {navController.navigate("settings")}
            )
        }
        composable("game") {
            GameScreen()
        }
        composable("settings") {
            SettingsScreen()
        }
    }
}

@Composable
fun Home(
    modifier: Modifier = Modifier,
    onNavigateToGame: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {}
) {

    Box(modifier = modifier.fillMaxSize() ){
        CenterDiv(
            modifier = modifier
                .align(Alignment.Center),
            onNavigateToGame = onNavigateToGame,
            onNavigateToSettings = onNavigateToSettings
        )

    }
}

@Composable
fun CenterDiv(modifier: Modifier = Modifier,
              onNavigateToGame: () -> Unit,
              onNavigateToSettings: () -> Unit = {}
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "" )
        Text(
            text = stringResource(id = R.string.app_name).uppercase(),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
        )
        Row(modifier = modifier.padding(16.dp)){
            Button(
                onClick = {
                    onNavigateToGame()
                },
                modifier = modifier.padding(end = 16.dp),
                elevation = ButtonDefaults.buttonElevation(16.dp)
            ) {
                Text(text = "Jogar")
            }
            OutlinedButton(
                onClick = { onNavigateToSettings() }
            ) {
                Text(text = "Configurações")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HangmanTheme {
        Home()
    }
}