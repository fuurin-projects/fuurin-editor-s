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

    fun copyWithOffset(offset: Offset): EventNode

    val screenValue: String?

    val leftConnector: Array<MutableList<EventNode>>

    val rightConnector: Array<MutableList<EventNode>>

}

enum class NodeType {

    INPUT, OUTPUT

}