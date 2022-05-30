package fuurineditor.ui.compose.parts.editor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.dp
import fuurineditor.service.data.event.EventNode
import fuurineditor.service.data.event.NodeType
import fuurineditor.ui.theme.BrightBackground
import org.jetbrains.skia.Font
import org.jetbrains.skia.Paint

@Composable
fun EventNodeCanvas(
    modifier: Modifier = Modifier,
    nodeList: List<EventNode>,
    onDragEnd: (offset: Offset, eventNode: EventNode) -> Unit
) {

    var dragEventNode: EventNode? by remember { mutableStateOf(null) }

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Canvas(modifier = modifier.pointerInput(nodeList) {
        //onDrag()
        detectDragGestures(
            onDragStart = { offset ->

                for (eventNode in nodeList) {
                    if (eventNode.offsetX < offset.x
                        && offset.x < eventNode.offsetX + 120.dp.toPx()
                        && eventNode.offsetY < offset.y
                        && offset.y < eventNode.offsetY + 100.dp.toPx()
                    ) {
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

                dragEventNode = null
                offsetX = 0f
                offsetY = 0f
            }
        ) { change, dragAmount ->
            change.consumeAllChanges()
            if (dragEventNode != null) {
                offsetX += dragAmount.x
                offsetY += dragAmount.y
            }

        }
    }) {

        for (eventNode in nodeList) {
            drawEventNode(
                eventNode = eventNode,
                moveOffset = if (dragEventNode == eventNode) Offset(offsetX, offsetY) else Offset.Zero
            )
        }

    }

}

fun DrawScope.drawEventNode(eventNode: EventNode, moveOffset: Offset) {

    translate(left = eventNode.offsetX + moveOffset.x, top = eventNode.offsetY + moveOffset.y) {

        val width = 120
        val height = 100

        //コンテンツ
        drawRoundRect(
            brush = Brush.linearGradient(colors = arrayListOf(BrightBackground, BrightBackground)),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
            style = Fill,
            size = Size(width.dp.toPx(), height.dp.toPx())
        )

        drawRoundRect(
            brush = Brush.linearGradient(colors = arrayListOf(eventNode.windowColor, eventNode.windowColor)),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round),
            size = Size(width.dp.toPx(), height.dp.toPx())
        )

        val headerWidth = width
        val headerHeight = 30
        val path = Path().apply {
            moveTo(0.dp.toPx(), headerHeight.dp.toPx())
            lineTo(headerWidth.dp.toPx(), headerHeight.dp.toPx())
            lineTo(headerWidth.dp.toPx(), 4.dp.toPx())
            arcTo(
                rect = Rect(
                    Offset((headerWidth - 8).dp.toPx(), 0.dp.toPx()),
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
                (width / 2f - (4 * eventNode.screenValue!!.length)).dp.toPx(),
                (height / 2f + 14).dp.toPx(),
                font = Font(typeface = null, size = 14.dp.toPx()),
                paint = paint2
            )
        }

        //右コネクトポイント
        if (eventNode.nodeType == NodeType.INPUT) {
            drawCircle(
                color = Color.White,
                center = Offset(x = (width - 1).dp.toPx(), y = (height / 2f).dp.toPx()),
                radius = 8.dp.toPx()
            )

            drawArc(
                color = eventNode.windowColor,
                startAngle = 90f,
                sweepAngle = -180f,
                useCenter = false,
                topLeft = Offset(x = (width - 1).dp.toPx(), y = (height / 2f).dp.toPx()).minus(
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
                center = Offset(x = (width - 1).dp.toPx(), y = (height / 2f).dp.toPx()),
                radius = 3.dp.toPx()
            )
        }

        if (eventNode.nodeType == NodeType.OUTPUT) {
            drawCircle(
                color = Color.White,
                center = Offset(x = (0 + 1).dp.toPx(), y = (height / 2f).dp.toPx()),
                radius = 8.dp.toPx()
            )

            drawArc(
                color = eventNode.windowColor,
                startAngle = 90f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(x = (0 + 1).dp.toPx(), y = (height / 2f).dp.toPx()).minus(
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
                center = Offset(x = (0 + 1).dp.toPx(), y = (height / 2f).dp.toPx()),
                radius = 3.dp.toPx()
            )
        }

    }


}