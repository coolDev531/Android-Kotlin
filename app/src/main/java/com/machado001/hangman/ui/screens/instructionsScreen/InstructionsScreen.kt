package com.machado001.hangman.ui.screens.instructionsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.machado001.hangman.R

@Composable
fun InstructionsScreen(
    onNavigateUp: () -> Unit,
) {
    InstructionsContent(onNavigateUp)
}

@Composable
private fun InstructionsContent(onNavigateUp: () -> Unit) {


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.INSTRUCTIONS_TITLE),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(end = 4.dp)
                )
                BookIcon()
            }
        }
        DividerWithPadding()
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.8f)

        ) {
            InstructionsStrings()
        }
        DividerWithPadding()
        BackButton(onNavigateUp = onNavigateUp)
    }
}

@Composable
private fun BookIcon() {
    Icon(
        painter = painterResource(id = R.drawable.baseline_menu_book_24),
        contentDescription = null,
        modifier = Modifier
            .padding(end = 4.dp)
    )
}

@Composable
private fun InstructionsStrings() {
    stringArrayResource(id = R.array.INSTRUCTIONS).forEach {
        Row {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowRight, contentDescription = null,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Text(
                text = it,
                style = MaterialTheme.typography.titleSmall,
            )
        }
        Spacer(modifier = Modifier.padding(bottom = 4.dp))
    }
}

@Composable
private fun BackButton(onNavigateUp: () -> Unit) {

    Button(onClick = onNavigateUp) {
        Text(text = stringResource(id = R.string.GO_BACK_TEXT))
    }
}

@Composable
private fun DividerWithPadding() {
    Divider(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(vertical = 16.dp),
        thickness = 2.dp,
        color = MaterialTheme.colorScheme.primary
    )
}