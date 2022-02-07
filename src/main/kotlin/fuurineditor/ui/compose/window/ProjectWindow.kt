package fuurineditor.ui.compose.window

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import fuurineditor.ui.theme.FuurinEditorTheme
import fuurineditor.ui.viewModel
import fuurineditor.viewmodel.EditorViewModel
import java.nio.file.Path

/**
 * プロジェクトウィンドウ
 */
@Composable
fun ProjectWindow(projectName: String, projectPath: Path, onCloseRequest: () -> Unit) {

    val state: WindowState = rememberWindowState(
        size = WindowSize(680.dp, 510.dp), position = WindowPosition(Alignment.Center)
    )

    Window(
        title = "${projectName} [${projectPath}] - Fuurin Editor",
        state = state,
        onCloseRequest = onCloseRequest,
        icon = painterResource("fuurin_icon_16.png"),
    ) {

        MenuBar {
            Menu(text = "hoge") {
                Item("New window", onClick = { })
            }
        }

        FuurinEditorTheme {

            val editorViewModel: EditorViewModel = viewModel()

            val count by editorViewModel.count.collectAsState()

            //EditorScreen()

            Column {

                Text(text = "title ${projectPath}", modifier = Modifier)

                Button(onClick = { editorViewModel.increment() }) {
                    Text(text = "${count}", modifier = Modifier)
                }
            }

        }

    }

}