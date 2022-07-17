package fuurineditor.app.service.data

import java.io.FilenameFilter
import java.io.InputStream
import java.nio.file.Path

fun java.io.File.toSceneFile(): SceneFile = SceneFile(JvmFile(this@toSceneFile))

class SceneFile(private val file: JvmFile) : File by file {

    /**
     * id
     */
    override val id: FileId
        get() = if (file.parent == null) FileId(
            "scene",
            file.name.replace(".json", "")
        ) else file.parent!!.id + file.name.replace(".json", "")

    /**
     * ディレクトリの場合は子要素があるかどうか
     */
    override val children: List<SceneFile>
        get() = file.rowFile
            .listFiles(FilenameFilter { _, name ->
                !name.startsWith(".")
                        && name.endsWith(".json")
                        && !name.startsWith("global.json")
            })//隠しフォルダは除外
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
