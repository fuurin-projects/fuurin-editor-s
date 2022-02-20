package fuurineditor.ui.data

sealed class FunctionType(val name: String) {
    object General : FunctionType("general")
    object Scene : FunctionType("scene")
    object Resources : FunctionType("resources")
    object Settings : FunctionType("settings")

}
