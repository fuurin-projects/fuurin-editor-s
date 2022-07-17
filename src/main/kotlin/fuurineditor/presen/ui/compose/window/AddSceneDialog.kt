package fuurineditor.ui.compose.window

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.window.Dialog
import fuurineditor.app.service.data.SceneType
import fuurineditor.ui.theme.Background

@Composable
fun AddSceneDialog(
    onCreateScene: (rowScene: RowScene) -> Unit = {},
    onCloseRequest: () -> Unit = {}
) {

    Dialog(
        title = "AddScene",
        onCloseRequest = onCloseRequest
    ) {

        var name by remember { mutableStateOf("") }


        Column(modifier = Modifier.fillMaxSize().background(Background)) {

            Text(text = "name")
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        println("onDone")
                        if (name.isNotEmpty()) {
                            onCreateScene(
                                RowScene(
                                    name = name, type = SceneType.WORLD
                                )
                            )
                        }
                    },
                    onGo = {
                        println("onGo")
                    },
                    onNext = {
                        println("onNext")
                    },
                    onSend = {
                        println("onSend")
                    }
                )
            )

            Button(
                onClick = {
                    onCreateScene(
                        RowScene(
                            name = name, type = SceneType.WORLD
                        )
                    )
                },
                modifier = Modifier,
                enabled = name.isNotEmpty()
            ) {
                Text(text = "Add")
            }

        }

    }

}

data class RowScene(
    val name: String,
    val type: SceneType
)