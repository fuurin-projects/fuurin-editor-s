package fuurineditor.service.data.event

import java.util.*

data class InputControllerNode(
    override val id: UUID = UUID.randomUUID(),
    override val nodeTypeName: String = "InputControllerNode",
    val type: String = "",
) : EventNode

enum class InputControllerKeyType(
    val type: String
) {
    UP("up"), DOWN("down"), LEFT("left"), RIGHT("right")
}
