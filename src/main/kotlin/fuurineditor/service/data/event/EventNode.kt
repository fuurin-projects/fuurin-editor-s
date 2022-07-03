package fuurineditor.service.data.event

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import java.util.*

interface EventNode {

    val id: UUID

    val nodeTypeName: String

    val nodeType: NodeType

    val windowColor: Color

    val offsetX: Float

    val offsetY: Float

    val width: Float

    val height: Float

    fun copyWithOffset(offset: Offset): EventNode

    val screenValue: String?

    val leftConnector: Array<MutableList<EventNode>>

    val rightConnector: Array<MutableList<EventNode>>

}

enum class NodeType {

    INPUT, OUTPUT

}

fun EventNode.isCollision(x: Float, y: Float): Boolean {
    return this@isCollision.offsetX < x
            && x < this@isCollision.offsetX + this@isCollision.width
            && this@isCollision.offsetY < y
            && y < this@isCollision.offsetY + this@isCollision.height
}