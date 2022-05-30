package fuurineditor.service.data.event

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import fuurineditor.ui.theme.OutputNodeColor
import java.util.*

data class OutputEventStateNode(
    override val id: UUID = UUID.randomUUID(),
    override val nodeTypeName: String = "EventStat",
    override val windowColor: Color = OutputNodeColor,
    override val offsetX: Float = 0f,
    override val offsetY: Float = 0f,
    val eventState: String = "moveX",
    override val screenValue: String = eventState,
    override val nodeType: NodeType = NodeType.OUTPUT,
) : EventNode {

    override fun copyWithOffset(offset: Offset): EventNode {
        return copy(offsetX = offset.x, offsetY = offset.y)
    }
}
