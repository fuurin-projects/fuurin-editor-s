package fuurineditor.presen.ui.data

sealed class FunctionType(val name: String) {
    object General : FunctionType("general")
    object Scene : FunctionType("scene")
    object Textures : FunctionType("textures")
    object Settings : FunctionType("settings")

}
