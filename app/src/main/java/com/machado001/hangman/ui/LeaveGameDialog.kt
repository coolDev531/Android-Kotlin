package com.machado001.hangman.ui

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import com.machado001.hangman.R

@Composable
fun LeaveGameDialog(finishActivity: () -> Unit = {}) {
    val openDialog = rememberSaveable { mutableStateOf(false) }

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
            title = { Text(stringResource(id = R.string.QUIT_HOME_TITLE).uppercase()) },
            confirmButton = {
                Button(onClick = finishActivity) {
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