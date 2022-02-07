package fuurineditor.ui.compose.screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp
import fuurineditor.service.SystemService
import fuurineditor.ui.LocalSpringContext
import fuurineditor.ui.stringResource
import fuurineditor.ui.theme.Background
import java.util.*


@Composable
fun LauncherScreen(
    onNewGameClick: () -> Unit = {},
    onOpenGameClick: () -> Unit = {}
) {

    val systemService = LocalSpringContext.current.getBean(
        SystemService::class.java
    )

    Column(
        modifier = Modifier.background(Color(0x00cccccc)).fillMaxWidth().fillMaxHeight().background(Background)
    ) {

        Spacer(modifier = Modifier.height(56.dp).align(Alignment.CenterHorizontally))
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
        Spacer(modifier = Modifier.height(48.dp).align(Alignment.CenterHorizontally))
        OutlinedButton(
            onClick = onNewGameClick,
            modifier = Modifier.align(Alignment.CenterHorizontally).widthIn(min = 280.dp)
        ) {
            Text(text = "新規でゲームを作成する")
        }
        Spacer(modifier = Modifier.height(24.dp).align(Alignment.CenterHorizontally))
        OutlinedButton(
            onClick = onOpenGameClick,
            modifier = Modifier.align(Alignment.CenterHorizontally).widthIn(min = 280.dp)
        ) {
            Text(text = "既存のゲームプロジェクトを開く")
        }
        
    }


}

@Composable
@Preview
fun PreviewLauncherScreen() {
    LauncherScreen(onNewGameClick = {})
}