package fuurineditor.service.data

interface File {

    val name: String
    val isDirectory: Boolean
    val children: List<File>
    val hasChildren: Boolean

}