package com.machado001.hangman.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.machado001.hangman.R
import com.machado001.hangman.ui.screens.instructionsScreen.BackButton
import com.machado001.hangman.ui.screens.instructionsScreen.BookIcon
import com.machado001.hangman.ui.screens.instructionsScreen.DividerWithPadding
import com.machado001.hangman.ui.theme.HangmanTheme


@Composable
fun TipDialog(onNavigateUp: () -> Unit) {
    Dialog(onDismissRequest = onNavigateUp) {
        Surface(
            shape = MaterialTheme.shapes.large,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 16.dp)
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
                            text = stringResource(id = R.string.DIALOG_TIP_TITLE),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        BookIcon(modifier = Modifier.align(Alignment.CenterVertically))
                    }
                }
                DividerWithPadding()
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowRight,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .offset(y = 2.dp)
                        )
                        Text(text = stringResource(id = R.string.DIALOG_TIP_TEXT))
                    }
                }
                DividerWithPadding()
                BackButton(onNavigateUp = onNavigateUp)
            }
        }
    }
}


@Preview(
    showSystemUi = true,
    showBackground = true,
)
@Composable
fun DialogsTipColumnPreview() {
    HangmanTheme {
        TipDialog(onNavigateUp = {})
    }
}