package fuurineditor.presen.ui.compose.window

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import fuurineditor.presen.ui.theme.Background
import fuurineditor.presen.ui.theme.FuurinEditorTheme

@Composable
fun AddCharacterTipDialog(
    onCloseRequest: () -> Unit = {}
) {

    Dialog(
        title = "AddCharacterTip",
        onCloseRequest = onCloseRequest
    ) {

        FuurinEditorTheme {

            Column(modifier = Modifier.fillMaxSize().background(Background)) {

            }

        }

    }

}