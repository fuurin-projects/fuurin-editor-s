package fuurineditor.ui.compose.parts.editor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Bolt
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material.icons.sharp.Edit
import androidx.compose.material.icons.sharp.FormatColorFill
import androidx.compose.material.icons.sharp.Image
import androidx.compose.material.icons.sharp.NorthWest
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import fuurineditor.service.data.SceneFile
import fuurineditor.ui.LocalProjectPathContext
import fuurineditor.ui.compose.parts.SubTab
import fuurineditor.ui.compose.parts.ToolButton
import fuurineditor.ui.compose.parts.VerticalDivider
import fuurineditor.ui.theme.Background
import fuurineditor.ui.theme.Border
import fuurineditor.ui.theme.SelectColor
import fuurineditor.ui.viewModel
import fuurineditor.viewmodel.editor.WorldSceneEditorViewModel

//26.5 * 15

val Transparent1 = Color(0xFF666666)
val Transparent2 = Color(0xFF999999)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WorldSceneEditor(sceneFile: SceneFile) {

    val viewModel: WorldSceneEditorViewModel = viewModel(LocalProjectPathContext.current, sceneFile)

    val nowTip by viewModel.nowTip.collectAsState()
    val selectTip by viewModel.selectTip.collectAsState()

    val layer by viewModel.layer.collectAsState()

    var select by remember { mutableStateOf<Offset?>(null) }

    var press by remember { mutableStateOf<Boolean>(false) }


    Column {

        Row(
            modifier = Modifier.height(28.dp).fillMaxWidth().background(Background)
        ) {


            SubTab(
                imageVector = Icons.Sharp.Image,
                text = "Layer",
                isSelect = true
            )

            SubTab(
                imageVector = Icons.Sharp.Bolt,
                text = "Event"
            )

            SubTab(
                imageVector = Icons.Sharp.Settings,
                text = "Setting"
            )

        }

        Divider(color = Border, thickness = 1.dp)

        Row {
            Column(modifier = Modifier.width(140.dp)) {

                Row(
                    modifier = Modifier.height(28.dp).fillMaxWidth().background(Background)
                ) {
                    ToolButton(imageVector = Icons.Sharp.NorthWest, onClick = {

                    })
                    ToolButton(imageVector = Icons.Sharp.Edit, onClick = {

                    })
                    ToolButton(imageVector = Icons.Sharp.FormatColorFill, onClick = {

                    })
                    ToolButton(imageVector = Icons.Sharp.Delete, onClick = {

                    })
                }
                Divider(color = Border, thickness = 1.dp)

                Row(modifier = Modifier.fillMaxSize()) {
                    if (nowTip != null) {
                        TiletipPanelList(root = nowTip!!, onClickTiletip = {
                            viewModel.selectTiletip(it)
                        })
                    } else {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

            }
            VerticalDivider(color = Border, thickness = 1.dp)
            Column(modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(1f).background(Background)) {

                Row(
                    modifier = Modifier.height(28.dp).fillMaxWidth().background(Background)
                ) {

                    SubTab(
                        imageVector = Icons.Sharp.Image,
                        text = "layer1",
                        isSelect = true
                    )

                }

                Divider(color = Border, thickness = 1.dp)

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    val density = LocalDensity.current
                    Canvas(
                        modifier = Modifier
                            .background(Color.Red).width(32.dp * 27).height(32.dp * 15)

                            .pointerInput(Unit) {

                                detectTapGestures(
                                    onPress = {
                                        println("${it.toSlot(this)}")
                                        viewModel.clickWorld(it.toSlot(this))
                                        press = true
                                    }
                                )
                            }.onPointerEvent(
                                eventType = PointerEventType.Release
                            ) {
                                val position = this.currentEvent.changes[0].position
                                println("${position.toSlot(this)}")
                                press = false
                            }
                            .pointerMoveFilter(
                                onMove = {
                                    select = it
                                    if (press) {
                                        viewModel.clickWorld(it.toSlot(density))
                                    }
                                    false
                                },
                                onEnter = {
                                    println("On Mouse(pointer) Enter")
                                    false
                                },
                                onExit = {
                                    println("on Mouse(pointer) Exit")
                                    select = null
                                    false
                                }
                            )
                    ) {


                        val slotX = 32.dp.roundToPx()
                        val slotY = 32.dp.roundToPx()

                        //表示されているエリアのみで描画する
                        clipRect() {
                            for (x in 0 until 27) {

                                for (y in 0 until 15) {

                                    if (layer.rowData[x][y] != null) {

                                        drawImage(
                                            image = layer.rowData[x][y]!!.texture,
                                            filterQuality = FilterQuality.None,
                                            dstOffset = IntOffset((x * slotX), (y * slotY)),
                                            dstSize = IntSize(slotX, slotY),
                                        )

                                    } else {
                                        drawRect(
                                            topLeft = Offset((x * slotX).toFloat(), (y * slotY).toFloat()),
                                            color = if ((x + y) % 2 == 0) Transparent1 else Transparent2,
                                            size = Size(slotX.toFloat(), slotY.toFloat()),
                                        )
                                    }


                                }

                            }

                            if (select != null) {
                                val slot = select!!.toSlot(this@Canvas)
                                drawRect(
                                    topLeft = Offset((slot.x * slotX).toFloat(), (slot.y * slotY).toFloat()),
                                    color = SelectColor,
                                    size = Size(slotX.toFloat(), slotY.toFloat()),
                                    style = Stroke(width = 2.dp.toPx()),
                                )
                            }


                        }


                    }

                }

            }
            VerticalDivider(color = Border, thickness = 1.dp)
            Column(modifier = Modifier.width(100.dp)) {

            }
        }

    }

}

data class Slot(val x: Int, val y: Int) {
    fun isIn(x: Int, y: Int): Boolean {
        return x == this.x && y == this.y
    }
}

//interface Density1 : Density {
//    fun Offset.toSlot(): Slot {
//
//        return Slot(x = (x / 32.dp.roundToPx()).toInt(), y = (y / 32.dp.roundToPx()).toInt())
//
//    }
//}
//typealias toSlotWithDensity = Density.(offset: Offset) -> Slot
//
//val toSlot: toSlotWithDensity = { offset ->
//    Slot(x = (offset.x / 32.dp.roundToPx()).toInt(), y = (offset.x / 32.dp.roundToPx()).toInt())
//}

//val toSlot2: Density.(Offset) -> Slot = fun Density.(offset): Slot {
//    return Slot(x = (offset.x / 32.dp.roundToPx()).toInt(), y = (offset.x / 32.dp.roundToPx()).toInt())
//}
//
//typealias toSlotWithDensity = Density.(offset: Offset) -> Slot
//
//fun Offset.toSlot(): Slot = {
//    return Slot(x = (x / 32.dp.roundToPx()).toInt(), y = (y / 32.dp.roundToPx()).toInt())
//}


//inline fun Offset.toSlot(
//    it: Density.(offset: Offset)
//    ): Slot {
//
//    return Slot(x = (x / 32.dp.roundToPx()).toInt(), y = (y / 32.dp.roundToPx()).toInt())
//}

//interface OffsetDensity : Density {
//    fun Offset.toSlot(): Slot {
//        return Slot(x = (x / 32.dp.roundToPx()).toInt(), y = (y / 32.dp.roundToPx()).toInt())
//    }
//}

fun Offset.toSlot(scope: Density): Slot = with(scope) {
    return Slot(x = (x / 32.dp.roundToPx()).toInt(), y = (y / 32.dp.roundToPx()).toInt())
}

//private fun Offset.toSlot(): Slot {
//    return Slot(x = (x / 32.dp.roundToPx()).toInt(), y = (y / 32.dp.roundToPx()).toInt())
//}


/*
fun Offset.toSlot(): Slot {

    return Slot(x = (x / 32.dp.roundToPx()).toInt(), y = (y / 32.dp.roundToPx()).toInt())

}*/


