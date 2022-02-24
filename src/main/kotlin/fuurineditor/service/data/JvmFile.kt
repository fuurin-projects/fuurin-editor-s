package fuurineditor.service.data

import java.io.FilenameFilter

fun java.io.File.toProjectFile(): File = JvmFile(this@toProjectFile)

class JvmFile(private val file: java.io.File) : File {

    val rowFile: java.io.File get() = file
    
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
            .map { it.toProjectFile() }

    /**
     *　子要素があるかどうか
     */
    override val hasChildren: Boolean
        get() = isDirectory && (file.listFiles()?.size ?: 0) > 0

}