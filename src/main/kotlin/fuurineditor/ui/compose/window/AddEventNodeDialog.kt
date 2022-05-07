package fuurineditor.ui.compose.window

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.rememberDialogState
import fuurineditor.service.data.event.EventNode
import fuurineditor.service.data.event.InputControllerNode
import fuurineditor.ui.data.NodeType
import fuurineditor.ui.theme.Background
import fuurineditor.ui.theme.Border
import fuurineditor.ui.theme.BrightBackground
import fuurineditor.ui.theme.SelectColor

@Composable
fun AddEventNodeDialog(
    onCreateEventNode: (eventNode: EventNode) -> Unit = {},
    onCloseRequest: () -> Unit = {}
) {

    val state: DialogState = rememberDialogState(size = DpSize(800.dp, 480.dp))

    var selectNode: NodeType by remember { mutableStateOf(NodeType.InputController) }

    var eventNode: EventNode? by remember { mutableStateOf(null) }

    Dialog(
        title = "AddEventNode",
        state = state,
        onCloseRequest = onCloseRequest
    ) {

        var name by remember { mutableStateOf("") }

        Column(modifier = Modifier.fillMaxSize().background(Background)) {

            Row(modifier = Modifier.weight(1f).fillMaxWidth()) {

                Column(modifier = Modifier.width(160.dp).fillMaxHeight().padding(8.dp).background(BrightBackground)) {

                    NodeItem(
                        NodeType.InputController.name,
                        selectNode = selectNode == NodeType.InputController,
                        onClock = {
                            eventNode = null
                            selectNode = NodeType.InputController
                        })

                    Divider(color = Border, thickness = 1.dp)

                    NodeItem(
                        NodeType.OutputEventState.name,
                        selectNode = selectNode == NodeType.OutputEventState,
                        onClock = {
                            eventNode = null
                            selectNode = NodeType.OutputEventState
                        })

                }


                Column(
                    modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(8.dp).fillMaxHeight()
                        .background(BrightBackground)
                ) {

                    if (selectNode == NodeType.InputController) {
                        InputControllerNodePanel(
                            inputControllerNode = if (eventNode is InputControllerNode) eventNode as InputControllerNode else InputControllerNode(),
                            onChangeEventNode = {
                                eventNode = it
                            }
                        )
                    }

                }

            }

            Divider(color = Border, thickness = 1.dp)

            Row(modifier = Modifier.fillMaxWidth().height(56.dp).padding(8.dp)) {
                Button(
                    onClick = {
                        //onCreateEventNode()
                    },
                    modifier = Modifier,
                    enabled = eventNode != null
                ) {
                    Text(text = "Add")
                }
            }


        }

    }

}

@Composable
fun NodeItem(
    name: String,
    selectNode: Boolean,
    onClock: () -> Unit = {}
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .drawBehind {
            drawPath(
                Path().apply {
                    val strokeWidthPx = 4.dp.toPx()
                    moveTo(0f, 0f)
                    lineTo(strokeWidthPx, strokeWidthPx)
                    val height = size.height
                    lineTo(strokeWidthPx, height - strokeWidthPx)
                    lineTo(0f, height)
                    close()
                },
                color = if (selectNode) SelectColor else Color.Unspecified
            )
        }.clickable {
            onClock()
        }.padding(8.dp)
    ) {
        Text(text = name)
    }
}

@Composable
fun InputControllerNodePanel(
    inputControllerNode: InputControllerNode,
    onChangeEventNode: (eventNode: InputControllerNode) -> Unit = {}
) {

    Column {

        Text(text = "key")

        OutlinedTextField(
            value = inputControllerNode.type,
            onValueChange = {
                onChangeEventNode(
                    InputControllerNode(
                        type = it
                    )
                )
            },
            singleLine = true
        )

    }

}
