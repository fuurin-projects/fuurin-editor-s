package fuurineditor.ui.compose

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window

@Composable
fun Launcher(onCloseRequest: () -> Unit) {

    Window(
        title = "Fuurin Editor",
        onCloseRequest = onCloseRequest,
        icon = painterResource("fuurin_icon_16.png")
    ) {

        DesktopMaterialTheme {

            Text("Hello, Desktop!")

        }

    }

}

@Composable
@Preview
fun PreviewLauncher() {

    Launcher(onCloseRequest = {});

}