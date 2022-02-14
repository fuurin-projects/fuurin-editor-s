package fuurineditor.ui.compose.screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp
import fuurineditor.service.data.toProjectState
import fuurineditor.ui.stringResource
import fuurineditor.ui.theme.Background
import fuurineditor.ui.theme.BrightBackground
import fuurineditor.ui.viewModel
import fuurineditor.viewmodel.GlobalViewModel
import fuurineditor.viewmodel.LauncherViewModel
import java.util.*


@Composable
fun LauncherScreen(
    onNewGameClick: () -> Unit = {},
    onOpenGameClick: () -> Unit = {}
) {

    val launcherViewModel: LauncherViewModel = viewModel()
    val projectInfoList by launcherViewModel.projectInfoList.collectAsState(null)

    val globalViewModel: GlobalViewModel = viewModel()


    Row(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().background(Background)
    ) {

        if (projectInfoList.isNullOrEmpty().not()) {
            LazyColumn(modifier = Modifier.background(BrightBackground).fillMaxWidth(0.5f).fillMaxHeight()) {

                items(projectInfoList!!) { projectInfo ->
                    Column(modifier = Modifier.fillMaxWidth().clickable {
                        globalViewModel.openProject(projectInfo.toProjectState())
                    }.padding(16.dp)) {

                        Text(text = projectInfo.name)

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(text = projectInfo.path.toString())
                    }

                }

            }
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            MainPanel(
                version = launcherViewModel.getVersion(),
                onNewGameClick = onNewGameClick,
                onOpenGameClick = onOpenGameClick
            )
        }


    }

}

@Composable
fun MainPanel(
    version: String = "0.0.0",
    onNewGameClick: () -> Unit = {},
    onOpenGameClick: () -> Unit = {}
) {

    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {

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
                "system_version_number", version, Locale.JAPANESE
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