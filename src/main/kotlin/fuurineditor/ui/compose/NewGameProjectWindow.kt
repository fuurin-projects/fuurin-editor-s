package fuurineditor.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.rememberDialogState
import fuurineditor.ui.theme.FuurinEditorTheme

@Composable
fun NewGameProjectWindow(onCloseRequest: () -> Unit = {}) {

    val state: DialogState = rememberDialogState(
        size = WindowSize(720.dp, 400.dp),
        position = WindowPosition(Alignment.Center)
    )

    Dialog(
        title = "新しいゲームを開発",
        state = state,
        onCloseRequest = onCloseRequest,
        icon = painterResource("fuurin_icon_16.png"),
    ) {

        FuurinEditorTheme {
            Column {
                Text("aaa");
            }
        }
        
    }

}