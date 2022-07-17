package fuurineditor.presen.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebView
import kotlinx.coroutines.launch


@Composable
fun BrowserWindow(url: String, onCloseRequest: () -> Unit = {}) {

    Window(title = "Web Browser", onCloseRequest = onCloseRequest) {

        val jFXPanel = remember { JFXPanel() }
        val rememberCoroutineScope = rememberCoroutineScope()

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            CircularProgressIndicator(
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .size(64.dp)
            )

            JavaFXPanel(root = window, panel = jFXPanel, onCreate = {
                Platform.runLater {
                    val view = WebView()
                    jFXPanel.scene = Scene(view)
                    view.engine.load(url)
                    rememberCoroutineScope.launch {

                    }
                }
            })


        }

    }

}