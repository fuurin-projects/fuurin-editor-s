package fuurineditor.service.data.event

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import fuurineditor.ui.theme.InputNodeColor
import java.util.*

data class InputControllerNode(
    override val id: UUID = UUID.randomUUID(),
    override val nodeTypeName: String = "InputController",
    override val windowColor: Color = InputNodeColor,
    val type: String = "up",
    override var offsetX: Float = 0f,
    override var offsetY: Float = 0f,
) : EventNode {

    override fun moveOffset(offset: Offset): EventNode {
        return copy(
            offsetX = offsetX + offset.x,
            offsetY = offsetY + offset.y
        )
    }
}

enum class InputControllerKeyType(
    val type: String
) {
    UP("up"), DOWN("down"), LEFT("left"), RIGHT("right")
}
