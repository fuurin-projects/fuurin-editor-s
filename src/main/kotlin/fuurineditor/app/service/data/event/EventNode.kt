package fuurineditor.app.service.data.event

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import java.util.*

abstract class EventNode {

    abstract val id: UUID

    abstract val nodeTypeName: String

    abstract val nodeType: NodeType

    abstract val windowColor: Color

    abstract val offsetX: Float

    abstract val offsetY: Float

    abstract val width: Float

    abstract val height: Float

    abstract fun copyWithOffset(offset: Offset): EventNode

    abstract val screenValue: String?

    abstract val leftConnector: Array<List<EventNode>>

    abstract val rightConnector: Array<List<EventNode>>

}

enum class NodeType {

    INPUT, OUTPUT

}

fun EventNode.offset(): Offset {
    return Offset(x = offsetX, y = offsetY)
}

fun EventNode.isCollision(x: Float, y: Float): Boolean {
    return this@isCollision.offsetX < x
            && x < this@isCollision.offsetX + this@isCollision.width
            && this@isCollision.offsetY < y
            && y < this@isCollision.offsetY + this@isCollision.height
}