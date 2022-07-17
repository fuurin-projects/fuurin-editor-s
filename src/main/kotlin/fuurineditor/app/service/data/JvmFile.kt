package fuurineditor.app.service.data

import java.io.FilenameFilter

fun java.io.File.toProjectFile(): File = JvmFile(this@toProjectFile)
fun java.io.File.toProjectFile(parentFile: File): File = JvmFile(this@toProjectFile, parentFile)

class JvmFile(private val file: java.io.File, private val parentFile: File? = null) : File {

    val rowFile: java.io.File get() = file

    /**
     * id
     */
    override val id: FileId get() = if (parentFile == null) FileId("unknown", file.name) else parentFile.id + file.name

    /**
     * 名前
     */
    override val name: String get() = file.name

    /**
     * ディレクトリなのかどうか
     */
    override val isDirectory: Boolean get() = file.isDirectory

    /**
     * ディレクトリの場合は子要素があるかどうか
     */
    override val children: List<File>
        get() = file
            .listFiles(FilenameFilter { _, name -> !name.startsWith(".") })//隠しフォルダは除外
            .orEmpty()
            .map { it.toProjectFile(this) }

    /**
     *　子要素があるかどうか
     */
    override val hasChildren: Boolean
        get() = isDirectory && (file.listFiles()?.size ?: 0) > 0

    /**
     * 親ファイル
     */
    override val parent: File? get() = parentFile

}