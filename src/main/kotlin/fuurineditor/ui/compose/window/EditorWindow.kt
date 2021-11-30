package fuurineditor.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import fuurineditor.ui.viewModel
import fuurineditor.viewmodel.EditorViewModel
import java.nio.file.Path

@Composable
fun Editor(title: String, projectPath: Path, onCloseRequest: () -> Unit) {

    val state: WindowState = rememberWindowState(
        size = WindowSize(680.dp, 510.dp), position = WindowPosition(Alignment.Center)
    )

    Window(state = state, onCloseRequest = onCloseRequest) {

        val editorViewModel: EditorViewModel = viewModel()

        val count by editorViewModel.count.collectAsState()

        Column {

            Text(text = "title ${projectPath}", modifier = Modifier)

            Button(onClick = { editorViewModel.increment() }) {
                Text(text = "${count}", modifier = Modifier)
            }
        }

    }

}