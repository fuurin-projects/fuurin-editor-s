package fuurineditor

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun Launcher(onCloseRequest: () -> Unit) {

    Window(
        title = "Fuurin Editor",
        onCloseRequest = onCloseRequest
    ) {

        DesktopMaterialTheme {

            Text("Hello, Desktop!")

        }

    }
}

fun main() = application {
    Launcher(onCloseRequest = ::exitApplication)
}
