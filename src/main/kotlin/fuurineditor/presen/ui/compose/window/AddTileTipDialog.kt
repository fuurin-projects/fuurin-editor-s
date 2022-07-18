package fuurineditor.presen.ui.compose.window

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.FolderOpen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState
import fuurineditor.presen.platform.fileChooser
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
        state = rememberDialogState(size = DpSize(600.dp, 400.dp)),
        onCloseRequest = onCloseRequest
    ) {

        val rememberCoroutineScope = rememberCoroutineScope()

        var name by remember { mutableStateOf("") }
        var displayName by remember { mutableStateOf("") }
        var location by remember { mutableStateOf("") }

        FuurinEditorTheme {

            Column(modifier = Modifier.fillMaxSize().background(Background).padding(16.dp)) {

                Text("リソースID", modifier = Modifier.padding(bottom = 4.dp))
                OutlinedTextField(
                    value = name, onValueChange = {
                        name = it
                    },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp).background(Color.White)
                )

                Text("表示名", modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))
                OutlinedTextField(
                    value = displayName, onValueChange = {
                        displayName = it
                    },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp).background(Color.White)
                )

                Text("取り込む画像の場所", modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))

                OutlinedTextField(
                    value = location,
                    onValueChange = {
                        location = it
                    },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp).background(Color.White),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Sharp.FolderOpen,
                            contentDescription = "",
                            modifier = Modifier.clickable {
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
                            }
                        )
                    }
                )

//                    Button(onClick = {
//
//                        rememberCoroutineScope.launch {
//
//                            val file = fileChooser(
//                                owner = window,
//                                title = "追加する画像を選択",
//                                extensionFilters = arrayListOf(
//                                    FileChooser.ExtensionFilter("イメージファイル", "*.png")
//                                )
//                            )
//
//                            if (file != null) {
//                                location = file.toPath().toString()
//                            }
//
//                            println(file?.toPath().toString())
//
//                        }
//
//                    }) {
//
//                        Text(text = "選択")
//                    }


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
