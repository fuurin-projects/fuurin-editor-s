package fuurineditor.service.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Image
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.loadImageBitmap
import fuurineditor.repository.data.TiletipJson
import fuurineditor.repository.data.toTiletipFileStatus
import fuurineditor.ui.compose.parts.CustomFileIcon
import fuurineditor.ui.compose.parts.CustomTreeNodeFile
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.FilenameFilter

fun java.io.File.toTiletipFile(parent: File? = null): TiletipFile = TiletipFile(JvmFile(this@toTiletipFile, parent))
fun JvmFile.toTiletipFile(): TiletipFile = TiletipFile(this@toTiletipFile)

class TiletipFile(val file: JvmFile) : File by file, CustomTreeNodeFile, CustomFileIcon {

    /**
     * id
     */
    override val id: FileId
        get() = if (file.parent == null) FileId(
            "tiletip",
            file.name.replace(".json", "")
        ) else file.parent!!.id + file.name.replace(".json", "")

    override val name: String get() = file.name.replace(".json", "")

    /**
     * ディレクトリの場合は子要素があるかどうか
     */
    override val children: List<TiletipFile>
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

    val texture: ImageBitmap by lazy {
        val imageFile = file.rowFile.toPath().resolve("../${file.rowFile.name.replace(".json", ".png")}").toFile()
        return@lazy loadImageBitmap(imageFile.inputStream())
    }
    override val fileIcon: ImageBitmap
        get() = texture

}

data class TiletipFileStatus(val displayName: String) {

}
