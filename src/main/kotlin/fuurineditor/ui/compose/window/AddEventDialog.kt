package fuurineditor.ui.compose.window

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import fuurineditor.service.data.event.Event
import fuurineditor.ui.theme.Background

@Composable
fun AddEventDialog(
    onCreateEvent: (event: Event) -> Unit = {},
    onCloseRequest: () -> Unit = {}
) {

    Dialog(
        title = "AddEvent",
        onCloseRequest = onCloseRequest
    ) {

        var name by remember { mutableStateOf("") }

        Column(modifier = Modifier.fillMaxSize().background(Background)) {

            Text(text = "name")
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                singleLine = true
            )

            Button(
                onClick = {
                    onCreateEvent(
                        Event(
                            name = name
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
