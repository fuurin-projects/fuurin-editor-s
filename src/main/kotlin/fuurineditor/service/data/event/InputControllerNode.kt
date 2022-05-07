package fuurineditor.service.data.event

data class InputControllerNode(
    val type: String = ""
) : EventNode

enum class InputControllerKeyType(
    val type: String
) {
    UP("up"), DOWN("down"), LEFT("left"), RIGHT("right")
}
