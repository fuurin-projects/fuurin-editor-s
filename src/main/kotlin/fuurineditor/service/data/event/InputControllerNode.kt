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
    override var offsetX: Float = 0f,
    override var offsetY: Float = 0f,
    override val screenValue: String = type.type
) : EventNode {

    override fun copyWithOffset(offset: Offset): EventNode {
        return copy(
            offsetX = offset.x,
            offsetY = offset.y
        )
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
