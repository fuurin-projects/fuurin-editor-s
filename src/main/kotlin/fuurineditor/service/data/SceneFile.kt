package fuurineditor.service.data

import java.io.FilenameFilter
import java.io.InputStream
import java.nio.file.Path

fun java.io.File.toSceneFile(): File = SceneFile(JvmFile(this@toSceneFile))

class SceneFile(private val file: JvmFile) : File by file {

    /**
     * ディレクトリの場合は子要素があるかどうか
     */
    override val children: List<File>
        get() = file.rowFile
            .listFiles(FilenameFilter { _, name -> !name.startsWith(".") && name.endsWith(".json") })//隠しフォルダは除外
            .orEmpty()
            .map { it.toSceneFile() }

    val inputStream: InputStream
        get() {
            return file.rowFile.inputStream()
        }

    val path: Path
        get() {
            return file.rowFile.toPath()
        }

}