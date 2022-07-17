package fuurineditor.presen.ui.compose.parts.editor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import fuurineditor.app.service.data.event.EventNode
import fuurineditor.app.service.data.event.isCollision
import fuurineditor.app.service.data.event.offset
import fuurineditor.presen.ui.theme.BrightBackground
import fuurineditor.presen.ui.theme.ConnectLineColor
import org.jetbrains.skia.Font
import org.jetbrains.skia.Paint

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EventNodeCanvas(
    modifier: Modifier = Modifier,
    nodeList: List<EventNode>,
    onDragEnd: (offset: Offset, eventNode: EventNode) -> Unit,
    onEventNodeConnect: (from: EventNode, to: EventNode) -> Unit
) {

    var dragEventNode: EventNode? by remember { mutableStateOf(null) }

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    var dragStartConnectEventNode: EventNode? by remember { mutableStateOf(null) }
    var mouseX by remember { mutableStateOf(0f) }
    var mouseY by remember { mutableStateOf(0f) }

    Canvas(modifier = modifier
        .pointerInput(nodeList) {
            //onDrag()
            detectDragGestures(
                onDragStart = { offset ->

                    for (eventNode in nodeList) {
                        if (eventNode.isLeftConnectorCollision(density = this, x = offset.x, y = offset.y)) {
                            dragStartConnectEventNode = eventNode
                        } else if (eventNode.isRightConnectorCollision(
                                density = this,
                                x = offset.x,
                                y = offset.y
                            )
                        ) {
                            dragStartConnectEventNode = eventNode
                        } else if (eventNode.isCollision(offset.x, offset.y)) {
                            dragEventNode = eventNode
                        }
                    }
                },
                onDragEnd = {
                    if (dragEventNode != null) {
                        onDragEnd(
                            Offset(
                                dragEventNode!!.offsetX + offsetX,
                                dragEventNode!!.offsetY + offsetY
                            ), dragEventNode!!
                        )
                    }

                    if (dragStartConnectEventNode != null) {
                        for (eventNode in nodeList) {
                            if (eventNode.isLeftConnectorCollision(this, mouseX, mouseY)) {
                                println("Connect！")
                                onEventNodeConnect(dragStartConnectEventNode!!, eventNode)
                            }
                        }
                    }

                    dragEventNode = null
                    offsetX = 0f
                    offsetY = 0f

                    dragStartConnectEventNode = null
                },
                onDrag = { change, dragAmount ->
                    change.consumeAllChanges()
                    if (dragEventNode != null) {
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }

                }
            )
        }.pointerMoveFilter(onMove = {
            //if (dragStartConnectEventNode != null) {
            mouseX = it.x
            mouseY = it.y
            //}
            false
        })
    ) {

        for (eventNode in nodeList) {
            drawEventNode(
                eventNode = eventNode,
                moveOffset = if (dragEventNode == eventNode) Offset(offsetX, offsetY) else Offset.Zero,
                mouseOffset = Offset(mouseX, mouseY)
            )
        }

        for (eventNode in nodeList) {
            drawConnect(
                eventNode = eventNode,
                moveOffset = if (dragEventNode == eventNode) Offset(offsetX, offsetY) else Offset.Zero,
                dragEventNode = dragEventNode,
                dragOffset = Offset(offsetX, offsetY)
            )
        }

        if (dragStartConnectEventNode != null) {
            drawTemConnectLine(eventNode = dragStartConnectEventNode!!, Offset(mouseX, mouseY))
        }

    }

}

fun DrawScope.drawEventNode(eventNode: EventNode, moveOffset: Offset, mouseOffset: Offset) {

    translate(left = eventNode.offsetX + moveOffset.x, top = eventNode.offsetY + moveOffset.y) {

        //コンテンツ
        drawRoundRect(
            brush = Brush.linearGradient(colors = arrayListOf(BrightBackground, BrightBackground)),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
            style = Fill,
            size = Size(eventNode.width, eventNode.height)
        )

        drawRoundRect(
            brush = Brush.linearGradient(colors = arrayListOf(eventNode.windowColor, eventNode.windowColor)),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round),
            size = Size(eventNode.width, eventNode.height)
        )

        val headerHeight = 30
        val path = Path().apply {
            moveTo(0.dp.toPx(), headerHeight.dp.toPx())
            lineTo(eventNode.width, headerHeight.dp.toPx())
            lineTo(eventNode.width, 4.dp.toPx())
            arcTo(
                rect = Rect(
                    Offset(eventNode.width - 8.dp.toPx(), 0.dp.toPx()),
                    Size(8.dp.toPx(), 8.dp.toPx())
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = -90f,
                forceMoveTo = false
            )
            lineTo(4.dp.toPx(), 0.dp.toPx())
            arcTo(
                rect = Rect(
                    Offset(0.dp.toPx(), 0.dp.toPx()),
                    Size(8.dp.toPx(), 8.dp.toPx())
                ),
                startAngleDegrees = 270f,
                sweepAngleDegrees = -90f,
                forceMoveTo = false
            )

            lineTo(0.dp.toPx(), 40.dp.toPx())
        }
        drawPath(
            path = path,
            brush = SolidColor(eventNode.windowColor)
        )

        val paint = Paint().apply {
            isAntiAlias = true
            color = Color.White.toArgb()
        }

        drawContext.canvas.nativeCanvas.drawString(
            eventNode.nodeTypeName,
            8.dp.toPx(),
            20.dp.toPx(),
            font = Font(typeface = null, size = 14.dp.toPx()),
            paint = paint
        )

        val paint2 = Paint().apply {
            isAntiAlias = true
            color = Color.Black.toArgb()
        }

        if (eventNode.screenValue != null) {
            drawContext.canvas.nativeCanvas.drawString(
                eventNode.screenValue!!,
                eventNode.width / 2f - (4 * eventNode.screenValue!!.length).dp.toPx(),
                eventNode.height / 2f + 14.dp.toPx(),
                font = Font(typeface = null, size = 14.dp.toPx()),
                paint = paint2
            )
        }

        //右コネクトポイント
        if (eventNode.rightConnector.isNotEmpty()) {
            drawCircle(
                color = Color.White,
                center = eventNode.getRightConnectorOffset(this).plus(Offset(x = 4.dp.toPx(), y = 4.dp.toPx())),
                radius = 8.dp.toPx()
            )

            drawArc(
                color = eventNode.windowColor,
                startAngle = 90f,
                sweepAngle = -180f,
                useCenter = false,
                topLeft = eventNode.getRightConnectorOffset(this).plus(Offset(x = 4.dp.toPx(), y = 4.dp.toPx())).minus(
                    Offset(
                        x = (16 / 2).dp.toPx(),
                        y = (16 / 2).dp.toPx()
                    )
                ),
                size = Size(width = 16.dp.toPx(), height = 16.dp.toPx()),
                style = Stroke(width = 2.dp.toPx())
            )

            drawCircle(
                color = Color.Blue,
                center = eventNode.getRightConnectorOffset(this).plus(Offset(x = 4.dp.toPx(), y = 4.dp.toPx())),
                radius = if (eventNode.isRightConnectorCollision(
                        this,
                        mouseOffset.x,
                        mouseOffset.y
                    )
                ) 4.dp.toPx() else 3.dp.toPx()
            )
        }

        //左コネクタ
        if (eventNode.leftConnector.isNotEmpty()) {
            drawCircle(
                color = Color.White,
                center = eventNode.getLeftConnectorOffset(this).plus(Offset(x = 4.dp.toPx(), y = 4.dp.toPx())),
                radius = 8.dp.toPx()
            )

            drawArc(
                color = eventNode.windowColor,
                startAngle = 90f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = eventNode.getLeftConnectorOffset(this).plus(Offset(x = 4.dp.toPx(), y = 4.dp.toPx())).minus(
                    Offset(
                        x = (16 / 2).dp.toPx(),
                        y = (16 / 2).dp.toPx()
                    )
                ),
                size = Size(width = 16.dp.toPx(), height = 16.dp.toPx()),
                style = Stroke(width = 2.dp.toPx())
            )

            drawCircle(
                color = Color.Blue,
                center = eventNode.getLeftConnectorOffset(this).plus(Offset(x = 4.dp.toPx(), y = 4.dp.toPx())),
                radius = if (eventNode.isLeftConnectorCollision(
                        this,
                        mouseOffset.x,
                        mouseOffset.y
                    )
                ) 4.dp.toPx() else 3.dp.toPx()
            )
        }

    }


}

fun DrawScope.drawConnect(eventNode: EventNode, moveOffset: Offset, dragEventNode: EventNode?, dragOffset: Offset) {

    translate(left = moveOffset.x, top = moveOffset.y) {

        for (lineList in eventNode.rightConnector) {

            for (targetEventNode in lineList) {

                if (dragEventNode == null || targetEventNode.id != dragEventNode.id) {
                    //通常
                    drawLine(
                        color = ConnectLineColor,
                        strokeWidth = 4.dp.toPx(),
                        start = Offset(
                            x = eventNode.offsetX + eventNode.width,
                            y = eventNode.offsetY + eventNode.height / 2
                        ),
                        end = Offset(
                            x = targetEventNode.offsetX - moveOffset.x,
                            y = targetEventNode.offsetY - moveOffset.y + eventNode.height / 2
                        ),
                    )
                } else {
                    //Connect先はdragされていた場合
                    drawLine(
                        color = ConnectLineColor,
                        strokeWidth = 4.dp.toPx(),
                        start = Offset(
                            x = eventNode.offsetX + eventNode.width,
                            y = eventNode.offsetY + eventNode.height / 2
                        ),
                        end = Offset(
                            x = targetEventNode.offsetX - moveOffset.x + dragOffset.x,
                            y = targetEventNode.offsetY - moveOffset.y + eventNode.height / 2 + dragOffset.y
                        ),
                    )
                }


            }

        }


    }

}

fun DrawScope.drawTemConnectLine(eventNode: EventNode, mouseOffset: Offset) {
    drawLine(
        color = ConnectLineColor,
        strokeWidth = 4.dp.toPx(),
        start = Offset(
            x = eventNode.offsetX + eventNode.width,
            y = eventNode.offsetY + eventNode.height / 2
        ),
        end = mouseOffset,
    )
}

fun EventNode.getRightConnectorOffset(density: Density): Offset {

    with(density) {

        val x = this@getRightConnectorOffset.width - 5.dp.toPx()
        val y = (this@getRightConnectorOffset.height / 2f) - 4.dp.toPx()

        return Offset(x = x, y = y)

    }

}

fun EventNode.isRightConnectorCollision(density: Density, x: Float, y: Float): Boolean {

    with(density) {
        val offset = this@isRightConnectorCollision.getRightConnectorOffset(density = density)
            .plus(Offset(4.dp.toPx(), 4.dp.toPx()))
            .plus(this@isRightConnectorCollision.offset())


        return offset.x - 8.dp.toPx() < x
                && x < offset.x + 8.dp.toPx()
                && offset.y - 8.dp.toPx() < y
                && y < offset.y + 8.dp.toPx()
    }

}


fun EventNode.getLeftConnectorOffset(density: Density): Offset {

    with(density) {

        val x = 0 - 3.dp.toPx()
        val y = (this@getLeftConnectorOffset.height / 2f) - 4.dp.toPx()

        return Offset(x = x, y = y)

    }

}

fun EventNode.isLeftConnectorCollision(density: Density, x: Float, y: Float): Boolean {

    with(density) {
        val offset = this@isLeftConnectorCollision
            .getLeftConnectorOffset(density = density)
            .plus(Offset(4.dp.toPx(), 4.dp.toPx()))
            .plus(this@isLeftConnectorCollision.offset())

        return offset.x - 8.dp.toPx() < x
                && x < offset.x + 8.dp.toPx()
                && offset.y - 8.dp.toPx() < y
                && y < offset.y + 8.dp.toPx()
    }

}