package fuurineditor.presen.ui.compose.window

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import fuurineditor.platform.fileChooser
import fuurineditor.presen.ui.theme.Background
import fuurineditor.presen.ui.theme.FuurinEditorTheme
import javafx.stage.FileChooser
import kotlinx.coroutines.launch
import java.nio.file.Path

@Composable
fun AddTileTipDialog(
    onAddTiletip: (data: RowTileTip) -> Unit = {},
    onCloseRequest: () -> Unit = {}
) {

    Dialog(
        title = "AddTileTip",
        onCloseRequest = onCloseRequest
    ) {

        val rememberCoroutineScope = rememberCoroutineScope()

        var name by remember { mutableStateOf("") }
        var displayName by remember { mutableStateOf("") }
        var location by remember { mutableStateOf("") }

        FuurinEditorTheme {

            Column(modifier = Modifier.fillMaxSize().background(Background)) {

                Text("Name")
                OutlinedTextField(value = name, onValueChange = {
                    name = it
                })

                Text("DisplayName")
                OutlinedTextField(value = displayName, onValueChange = {
                    displayName = it
                })

                Text("Locale")
                Row {

                    OutlinedTextField(value = location, onValueChange = {
                        location = it
                    })

                    Button(onClick = {

                        rememberCoroutineScope.launch {

                            val file = fileChooser(
                                owner = window,
                                title = "追加する画像を選択",
                                extensionFilters = arrayListOf(
                                    FileChooser.ExtensionFilter("イメージファイル", "*.png")
                                )
                            )

                            if (file != null) {
                                location = file.toPath().toString()
                            }

                            println(file?.toPath().toString())

                        }

                    }) {

                        Text(text = "選択")
                    }
                }

                Button(onClick = {

                    if (name.isNotBlank() && location.isNotBlank()) {
                        onAddTiletip(
                            RowTileTip(
                                name = name,
                                displayName = if (displayName.isNotBlank()) displayName else name,
                                location = Path.of(location)
                            )
                        )
                    }

                    onCloseRequest()
                }) {
                    Text("Add")
                }
            }

        }

    }

}

data class RowTileTip(
    val name: String,
    val displayName: String,
    val location: Path
)
