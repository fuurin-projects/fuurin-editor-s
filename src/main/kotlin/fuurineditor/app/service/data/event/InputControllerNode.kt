package fuurineditor.service.data.event

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import fuurineditor.ui.theme.InputNodeColor
import java.util.*

data class InputControllerNode(
    override val id: UUID = UUID.randomUUID(),
    override val nodeTypeName: String = "Controller",
    override val windowColor: Color = InputNodeColor,
    val type: InputControllerKeyType = InputControllerKeyType.UP,
    override val offsetX: Float = 0f,
    override val offsetY: Float = 0f,
    override val width: Float = 120f,
    override val height: Float = 100f,
    override val screenValue: String = type.type,
    override val nodeType: NodeType = NodeType.INPUT,
    override val leftConnector: Array<List<EventNode>> = arrayOf(),
    override val rightConnector: Array<List<EventNode>> = arrayOf(mutableListOf()),
) : EventNode {

    override fun copyWithOffset(offset: Offset): EventNode {
        return copy(
            offsetX = offset.x,
            offsetY = offset.y
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InputControllerNode

        if (id != other.id) return false
        if (nodeTypeName != other.nodeTypeName) return false
        if (windowColor != other.windowColor) return false
        if (type != other.type) return false
        if (offsetX != other.offsetX) return false
        if (offsetY != other.offsetY) return false
        if (screenValue != other.screenValue) return false
        if (nodeType != other.nodeType) return false
        if (this !== other) return false
        if (!leftConnector.contentEquals(other.leftConnector)) return false
        if (!rightConnector.contentEquals(other.rightConnector)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + nodeTypeName.hashCode()
        result = 31 * result + windowColor.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + offsetX.hashCode()
        result = 31 * result + offsetY.hashCode()
        result = 31 * result + screenValue.hashCode()
        result = 31 * result + nodeType.hashCode()
        result = 31 * result + leftConnector.contentHashCode()
        result = 31 * result + rightConnector.contentHashCode()
        return result
    }


}

enum class InputControllerKeyType(
    val type: String
) {
    UP("up"), DOWN("down"), LEFT("left"), RIGHT("right");

    companion object {
        fun fromString(type: String): InputControllerKeyType {
            return InputControllerKeyType.values().find {
                it.type == type
            } ?: InputControllerKeyType.UP
        }
    }

}
