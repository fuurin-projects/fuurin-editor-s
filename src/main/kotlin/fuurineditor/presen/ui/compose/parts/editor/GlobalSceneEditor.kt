package fuurineditor.presen.ui.compose.parts.editor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AddBox
import androidx.compose.material.icons.sharp.Bolt
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp
import fuurineditor.app.service.data.SceneFile
import fuurineditor.app.service.data.event.Event
import fuurineditor.app.service.data.event.EventNode
import fuurineditor.presen.ui.LocalProjectPathContext
import fuurineditor.presen.ui.compose.parts.SubTab
import fuurineditor.presen.ui.compose.parts.ToolButton
import fuurineditor.presen.ui.compose.parts.TreeNode
import fuurineditor.presen.ui.compose.parts.VerticalDivider
import fuurineditor.presen.ui.compose.window.AddEventDialog
import fuurineditor.presen.ui.compose.window.AddEventNodeDialog
import fuurineditor.presen.ui.theme.Background
import fuurineditor.presen.ui.theme.BlueprintBackground
import fuurineditor.presen.ui.theme.BlueprintBorder
import fuurineditor.presen.ui.theme.Border
import fuurineditor.presen.ui.theme.BrightBackground
import fuurineditor.presen.ui.viewModel
import fuurineditor.viewmodel.editor.GlobalSceneEditorViewModel

@Composable
fun GlobalSceneEditor(sceneFile: SceneFile) {

    val viewModel: GlobalSceneEditorViewModel = viewModel(LocalProjectPathContext.current, sceneFile)

    val globalScene by viewModel.globalScene.collectAsState()
    val selectEvent by viewModel.selectEvent.collectAsState()

    SideEffect {
        println("GlobalSceneEditor")
    }

    var editMode by remember { mutableStateOf<EditMode>(EditMode.EVENT) }

    Column {

        Row(
            modifier = Modifier.height(28.dp).fillMaxWidth().background(Background)
        ) {

            SubTab(
                imageVector = Icons.Sharp.Bolt,
                text = "Event",
                isSelect = editMode == EditMode.EVENT,
                onClick = {
                    editMode = EditMode.EVENT
                }
            )

            SubTab(
                imageVector = Icons.Sharp.Settings,
                text = "Setting",
                isSelect = editMode == EditMode.SETTING,
                onClick = {
                    editMode = EditMode.SETTING
                }
            )


        }

        Divider(color = Border, thickness = 1.dp)


        if (editMode == EditMode.EVENT) {

            SideEffect {
                println("EventBoardPre")
            }

            EventBoard(
                eventList = globalScene?.eventList ?: arrayListOf(),
                selectEvent = selectEvent,
                onSelectEvent = {
                    viewModel.onSelectEvent(it)
                },
                onAddEvent = {
                    viewModel.addEvent(it)
                },
                onAddEventNode = {
                    viewModel.addEventNode(it)
                },
                onEventNodeonDragEnd = { event, eventNode, offset ->
                    viewModel.dragEventNode(
                        event = event,
                        eventNode = eventNode,
                        offset = offset
                    )
                },
                onEventNodeConnect = { event, from, to ->
                    viewModel.connectEventNode(
                        event = event,
                        from = from,
                        to = to
                    )
                }
            )
        } else {
            Text(text = "setting")
        }

    }


}

@Composable
fun EventBoard(
    eventList: List<Event> = arrayListOf(),
    selectEvent: Event?,
    onSelectEvent: (event: Event) -> Unit = {},
    onAddEvent: (event: Event) -> Unit = {},
    onAddEventNode: (eventNode: EventNode) -> Unit = {},
    onEventNodeonDragEnd: (event: Event, eventNode: EventNode, offset: Offset) -> Unit,
    onEventNodeConnect: (event: Event, from: EventNode, to: EventNode) -> Unit,
) {

    SideEffect {
        println("EventBoard")
    }

    var addEventDialog by remember { mutableStateOf(false) }

    if (addEventDialog) {
        AddEventDialog(
            onCreateEvent = {
                onAddEvent(it)
                addEventDialog = false
            },
            onCloseRequest = {
                addEventDialog = false
            }
        )
    }

    var addEventNodeDialog by remember { mutableStateOf(false) }

    if (addEventNodeDialog) {
        AddEventNodeDialog(
            onCreateEventNode = {
                onAddEventNode(it)
                addEventNodeDialog = false
            },
            onCloseRequest = {
                addEventNodeDialog = false
            }
        )
    }

    Row {
        Column(modifier = Modifier.width(140.dp)) {

            Row(
                modifier = Modifier.height(28.dp).fillMaxWidth().background(Background)
            ) {
                ToolButton(imageVector = Icons.Sharp.AddBox, onClick = {
                    addEventDialog = true
                })
            }

            Divider(color = Border, thickness = 1.dp)

            for (event in eventList) {

                TreeNode(
                    imageVector = Icons.Sharp.Bolt,
                    text = event.name,
                    deep = 0,
                    idDirectory = false,
                    folding = true,
                    onDoubleClick = {
                        onSelectEvent(event)
                    }
                )

            }

        }

        VerticalDivider(color = Border, thickness = 1.dp)
        Column(modifier = Modifier.width(100.dp)) {

            Row(
                modifier = Modifier.height(28.dp).fillMaxWidth().background(Background)
            ) {
                ToolButton(imageVector = Icons.Sharp.AddBox, onClick = {
                    addEventNodeDialog = true
                })
            }

            Divider(color = Border, thickness = 1.dp)

        }

        VerticalDivider(color = Border, thickness = 1.dp)
        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(1f).background(Background)) {

            Row(
                modifier = Modifier.height(28.dp).fillMaxWidth().background(Background)
            ) {

                for (event in eventList) {
                    SubTab(
                        imageVector = Icons.Sharp.Bolt,
                        text = event.name,
                        isSelect = if (selectEvent == null) false else selectEvent.name == event.name,
                        onClick = {
                            onSelectEvent(event)
                        }
                    )
                }

            }

            Divider(color = Border, thickness = 1.dp)

            Column {
                if (selectEvent != null) {

                    // Nodeを表示するところ
                    Box(modifier = Modifier.fillMaxSize()) {
                        Canvas(modifier = Modifier.fillMaxSize().background(BlueprintBackground)) {

                            val slotX = 32.dp.roundToPx()
                            val slotY = 32.dp.roundToPx()

                            //表示されているエリアのみで描画する
                            clipRect() {


                                for (x in 0 until (size.width / slotX).toInt() + 2) {

                                    for (y in 0 until (size.height / slotY).toInt() + 2) {
                                        drawRect(
                                            topLeft = Offset(
                                                (x * slotX).toFloat() - slotX / 2,//半分ずらす
                                                (y * slotY).toFloat() - slotY / 2//半分ずらす
                                            ),
                                            color = BlueprintBorder,
                                            size = Size(slotX.toFloat(), slotY.toFloat()),
                                            style = Stroke(width = 1.dp.toPx()),
                                        )
                                    }
                                }

                            }
                        }
                        EventNodeCanvas(
                            modifier = Modifier.fillMaxSize(),
                            nodeList = selectEvent.nodeList,
                            onDragEnd = { offset: Offset, eventNode: EventNode ->
                                onEventNodeonDragEnd(selectEvent, eventNode, offset)
                            },
                            onEventNodeConnect = { from, to ->
                                onEventNodeConnect(selectEvent, from, to)
                            }
                        )
//                        Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
//                            for (eventNode in selectEvent.nodeList) {
//                                key(eventNode.id.toString()) {
//
//                                    EventNodeWindow(
//                                        eventNode = eventNode,
//                                        onDragEnd = {
//                                            onEventNodeonDragEnd(selectEvent, eventNode, it)
//                                        }
//                                    )
//
//                                    SideEffect {
//                                        println("EventNodeWindow")
//                                    }
//
//
//                                }
//                            }
//                        }
                    }


                } else {

                    Column(
                        modifier = Modifier.fillMaxSize().background(BrightBackground),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = "Eventが選択されていません")
                    }

                }
            }


        }


    }

}

/*
@Composable
fun EventNodeWindow(
    eventNode: EventNode,
    onDragEnd: (offset: Offset) -> Unit
) {

    var offsetX by remember { mutableStateOf(eventNode.offsetX) }
    var offsetY by remember { mutableStateOf(eventNode.offsetY) }

    Column(
        modifier = Modifier
            .offset {
                IntOffset(
                    (offsetX).roundToInt(),
                    (offsetY).roundToInt()
                )
            }
            .padding(16.dp)
            .width(120.dp)
            .height(100.dp)
            .shadow(4.dp, shape = RoundedCornerShape(4.dp))
            .clip(shape = RoundedCornerShape(4.dp))
            .border(width = 2.dp, eventNode.windowColor, shape = RoundedCornerShape(4.dp))
            .background(BrightBackground)
    ) {

        Text(
            modifier = Modifier
                .background(eventNode.windowColor)
                .pointerInput(Unit) {
                    //onDrag()
                    detectDragGestures(
                        onDragEnd = {
                            onDragEnd(Offset(offsetX, offsetY))
                        }
                    ) { change, dragAmount ->
                        change.consumeAllChanges()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
                .fillMaxWidth()
                .padding(8.dp),
            text = "${eventNode.nodeTypeName}",
            color = Color.White
        )
        Divider(color = Border, thickness = 1.dp)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (eventNode is InputControllerNode) {
                Text(text = eventNode.type.toString())
            }

        }

    }

}*/