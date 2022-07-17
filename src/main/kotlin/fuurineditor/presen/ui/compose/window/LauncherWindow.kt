package fuurineditor.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import com.sun.glass.ui.Application
import com.sun.glass.ui.CommonDialogs
import fuurineditor.ui.compose.screen.LauncherScreen
import fuurineditor.ui.data.ProjectState
import fuurineditor.ui.theme.FuurinEditorTheme
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.stage.DirectoryChooser
import kotlinx.coroutines.launch
import java.io.File


@Composable
fun LauncherWindow(onCloseRequest: () -> Unit, openProject: (ProjectState) -> Unit = {}) {

    val state: WindowState = rememberWindowState(
        size = WindowSize(680.dp, 510.dp), position = WindowPosition(Alignment.Center)
    )
    Window(
        title = "Fuurin Editor",
        onCloseRequest = onCloseRequest,
        icon = painterResource("fuurin_icon_16.png"),
        state = state,
    ) {

        var openNewGame by remember { mutableStateOf(false) }

        val rememberCoroutineScope = rememberCoroutineScope()

        FuurinEditorTheme {

            LauncherScreen(
                onNewGameClick = { openNewGame = true },
                onOpenGameClick = {

                    rememberCoroutineScope.launch {

                        JFXPanel()
                        Platform.runLater {

                            var directoryChooser = DirectoryChooser()
                            directoryChooser.title = "ゲームプロジェクトを選択"

                            try {


                                val nativeWindow = Application.GetApplication().createWindow(window.windowHandle)

                                val file: File? = CommonDialogs.showFolderChooser(
                                    nativeWindow,
                                    directoryChooser.initialDirectory,
                                    directoryChooser.title
                                )
                                if (file != null) {
                                    openProject(ProjectState(name = "test", path = file.toPath()))
                                };

                            } finally {

                            }

                        }

                    }

                }
            )
        }

        if (openNewGame) {
            NewGameProjectWindow(onCloseRequest = { openNewGame = false })
        }


        //JavaFXPanel(root = window, jFXPanel, onCreate = {})

        /*
        if (isFileChooserOpen) {
            FileDialog(
                parent = window,
                onCloseRequest = {
                    isFileChooserOpen = false
                    println("Result $it")
                }
            )

            //BrowserWindow(onCloseRequest = { openOpenGame = false }, url = "https://www.google.com/?hl=ja")
        }*/

    }

}

