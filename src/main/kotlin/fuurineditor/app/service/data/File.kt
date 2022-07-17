package fuurineditor.service.data

interface File {

    val id: FileId
    val name: String
    val isDirectory: Boolean
    val children: List<File>
    val hasChildren: Boolean
    val parent: File?

}

data class FileId(
    val type: String,
    val path: String
)

fun FileId.toIndexKye(): String {
    return "$type@$path"
}


fun fromIndexKey(key: String): FileId {
    val split = key.split("@")
    return FileId(split[0], split[1])
}

operator fun FileId.plus(path: String): FileId {
    return FileId(this.type, this.path + "/" + path)
}