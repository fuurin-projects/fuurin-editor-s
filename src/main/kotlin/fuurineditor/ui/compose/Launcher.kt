package fuurineditor.ui.compose

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import fuurineditor.service.SystemService
import fuurineditor.ui.LocalSpringContext
import fuurineditor.ui.stringResource
import fuurineditor.ui.theme.FuurinEditorTheme
import java.util.*

@Composable
fun Launcher(onCloseRequest: () -> Unit) {

    Window(
        title = "Fuurin Editor",
        onCloseRequest = onCloseRequest,
        icon = painterResource("fuurin_icon_16.png")
    ) {

        FuurinEditorTheme {

            val systemService = LocalSpringContext.current.getBean(
                SystemService::class.java
            )

            Column(
                modifier = Modifier.background(Color(0x00cccccc)).fillMaxWidth().fillMaxHeight()
            ) {
                Spacer(modifier = Modifier.height(80.dp).align(Alignment.CenterHorizontally))
                Image(
                    bitmap = useResource("fuurin_icon_16.png") { loadImageBitmap(it) },
                    contentDescription = "image",
                    filterQuality = FilterQuality.None,
                    modifier = Modifier.size(128.dp).align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp).align(Alignment.CenterHorizontally))
                Text(text = "Fuurin Editor", modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(16.dp).align(Alignment.CenterHorizontally))
                Text(
                    text = stringResource(
                        "system_version_number", systemService.getVersion(), Locale.JAPANESE
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

        }

    }

}

@Composable
@Preview
fun PreviewLauncher() {
    Launcher(onCloseRequest = {})
}