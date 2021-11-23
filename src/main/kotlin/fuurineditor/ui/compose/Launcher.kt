package fuurineditor.ui.compose

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import fuurineditor.ui.theme.FuurinEditorTheme

@Composable
fun Launcher(onCloseRequest: () -> Unit) {

    Window(
        title = "Fuurin Editor",
        onCloseRequest = onCloseRequest,
        icon = painterResource("fuurin_icon_16.png")
    ) {

        FuurinEditorTheme {

            Row {

                Text("Hello, Desktop!")
                Button(onClick = {}) {
                    Text(text = "Hoge")
                }
            }

        }

    }

}

@Composable
@Preview
fun PreviewLauncher() {
    Launcher(onCloseRequest = {})
}