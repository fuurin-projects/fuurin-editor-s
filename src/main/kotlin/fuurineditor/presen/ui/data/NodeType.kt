package fuurineditor.presen.ui.data


sealed class NodeType(val name: String) {
    object InputController : NodeType("Controller")
    object OutputEventState : NodeType("EventState")

}