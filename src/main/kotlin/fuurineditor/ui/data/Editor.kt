package fuurineditor.ui.data

import fuurineditor.service.data.File

data class Editor(
    val file: File
)

val EmptyEditor = Editor(file = object : File {
    override val name: String
        get() = TODO("Not yet implemented")
    override val isDirectory: Boolean
        get() = TODO("Not yet implemented")
    override val children: List<File>
        get() = TODO("Not yet implemented")
    override val hasChildren: Boolean
        get() = TODO("Not yet implemented")

})