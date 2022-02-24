package fuurineditor.service.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Image
import androidx.compose.ui.graphics.vector.ImageVector
import fuurineditor.repository.data.TiletipJson
import fuurineditor.repository.data.toTiletipFileStatus
import fuurineditor.ui.compose.parts.CustomTreeNodeFile
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.FilenameFilter

fun java.io.File.toTiletipFile(): File = TiletipFile(JvmFile(this@toTiletipFile))
fun JvmFile.toTiletipFile(): File = TiletipFile(this@toTiletipFile)

class TiletipFile(private val file: JvmFile) : File by file, CustomTreeNodeFile {

    /**
     * ディレクトリの場合は子要素があるかどうか
     */
    override val children: List<File>
        get() = file.rowFile
            .listFiles(FilenameFilter { _, name -> !name.startsWith(".") && name.endsWith(".json") })//隠しフォルダは除外
            .orEmpty()
            .map { it.toTiletipFile() }

    val status: TiletipFileStatus by lazy {
        Json.decodeFromString<TiletipJson>(file.rowFile.readText()).toTiletipFileStatus()
    }
    override val customName: String by lazy {
        status.displayName
    }
    override val fakeIcon: ImageVector
        get() = Icons.Sharp.Image

}

data class TiletipFileStatus(val displayName: String) {

}
