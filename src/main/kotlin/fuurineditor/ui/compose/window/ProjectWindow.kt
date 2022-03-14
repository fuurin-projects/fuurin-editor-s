package fuurineditor.ui.compose.window

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import fuurineditor.service.data.ProjectData
import fuurineditor.ui.LocalProjectPathContext
import fuurineditor.ui.compose.screen.ProjectScreen
import fuurineditor.ui.theme.FuurinEditorTheme
import fuurineditor.ui.viewModel
import fuurineditor.viewmodel.ProjectViewModel
import java.nio.file.Path

/**
 * プロジェクトウィンドウ
 */
@Composable
fun ProjectWindow(projectName: String, projectPath: Path, onCloseRequest: () -> Unit) {

    val state: WindowState = rememberWindowState(
        size = DpSize(1280.dp, 720.dp), position = WindowPosition(Alignment.Center)
    )

    Window(
        title = "${projectName} [${projectPath}] - Fuurin Editor",
        state = state,
        onCloseRequest = onCloseRequest,
        icon = painterResource("fuurin_icon_16.png"),
    ) {

        MenuBar {
            Menu(text = "File") {
                Item("New window", onClick = { })
            }
        }

        CompositionLocalProvider(LocalProjectPathContext provides projectPath) {

            FuurinEditorTheme {

                val editorViewModel: ProjectViewModel = viewModel(LocalProjectPathContext.current)

                val usState by editorViewModel.uiState.collectAsState()
                val editors by editorViewModel.editors.collectAsState()
                val selectEditor by editorViewModel.selectedEditor.collectAsState()
                val count by editorViewModel.count.collectAsState()
                val projectData by editorViewModel.projectData.collectAsState(ProjectData(name = "loading..."))
                val sceneList by editorViewModel.sceneList.collectAsState(null)
                val tiletipList by editorViewModel.tiletipList.collectAsState(null)

                ProjectScreen(
                    usState = usState,
                    editors = editors,
                    selectedEditor = selectEditor,
                    sceneList = sceneList,
                    tiletipList = tiletipList,
                    onClickFunctionButton = {
                        editorViewModel.changeFunctionType(it)
                    },
                    onCreateScene = {
                        editorViewModel.onAddScene(it)
                    },
                    onAddTileTip = {
                        editorViewModel.onAddTileTip(it)
                    },
                    addEditor = {
                        editorViewModel.addEditor(file = it)
                    },
                    onClickEditor = {
                        editorViewModel.selectEditor(file = it.file)
                    },
                    closeEditor = {
                        editorViewModel.closeEditor(it)
                    }
                )
//
//            EditorScreen()
//
//            Column {
//
//                Text(
//                    text = "title ${projectPath} ${projectData.name}",
//                    modifier = Modifier
//                )
//
//                Button(onClick = { editorViewModel.increment() }) {
//                    Text(text = "${count}", modifier = Modifier)
//                }
//            }

            }

        }

    }

}