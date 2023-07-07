package com.machado001.hangman.ui.components.dialogs

import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.machado001.hangman.R


@Composable
fun BackToHomeDialog(onNavigateUp: () -> Unit) {
    val openDialog = remember { mutableStateOf(false) }

    BackHandler(onBack = { openDialog.value = true })

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.ExitToApp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            title = { Text(stringResource(id = R.string.QUIT_GAME_CONFIRMATION_TEXT_TITLE).uppercase()) },
            text = {
                Text(
                    stringResource(id = R.string.QUIT_GAME_CONFIRMATION_TEXT_DESCRIPTION),
                    fontSize = 16.sp
                )
            },
            confirmButton = {
                Button(onClick = onNavigateUp) {
                    Text(stringResource(id = R.string.YES_TEXT))
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { openDialog.value = false }) {
                    Text(stringResource(id = R.string.NO_TEXT))
                }
            }
        )
    }
}