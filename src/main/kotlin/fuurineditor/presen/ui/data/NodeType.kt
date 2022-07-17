package fuurineditor.ui.data


sealed class NodeType(val name: String) {
    object InputController : NodeType("InputControllerNode")
    object OutputEventState : NodeType("OutputEventState")

}