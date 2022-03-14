package fuurineditor.service.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Image
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.loadImageBitmap
import fuurineditor.repository.data.TiletipJson
import fuurineditor.repository.data.toTiletipFileStatus
import fuurineditor.ui.compose.parts.CustomTreeNodeFile
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.FilenameFilter

fun java.io.File.toTiletipFile(): TiletipFile = TiletipFile(JvmFile(this@toTiletipFile))
fun JvmFile.toTiletipFile(): TiletipFile = TiletipFile(this@toTiletipFile)

class TiletipFile(private val file: JvmFile) : File by file, CustomTreeNodeFile {

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

}

data class TiletipFileStatus(val displayName: String) {

}
